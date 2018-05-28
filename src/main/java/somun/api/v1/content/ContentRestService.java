package somun.api.v1.content;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.util.JsoupExtends;
import somun.exception.APIServerException;
import somun.service.auth.WebCertService;
import somun.service.repository.AutoComplite;
import somun.service.repository.ContentActivity;
import somun.service.repository.ContentActivityRepository;
import somun.service.repository.ContentAlarm;
import somun.service.repository.ContentAlarmRepository;
import somun.service.repository.ContentComment;
import somun.service.repository.ContentCommentRepository;
import somun.service.repository.ContentThumbUp;
import somun.service.repository.ContentThumbUpRepository;
import somun.service.repository.EventContent;
import somun.service.repository.EventContentRepository;
import somun.service.repository.EventLocation;
import somun.service.repository.EventLocationRepository;
import somun.service.repository.User;
import somun.service.repository.UserRepository;
import somun.service.repositoryComb.ContentActivityComb;
import somun.service.repositoryComb.ContentCommentWithUser;
import somun.service.repositoryComb.EventContentWithUser;
import somun.service.repositoryComb.WebCertInfo;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="Content/V1/")
@Api(value = "Content/V1/", description = "Content Service", tags = {"Content"})
@ApiResponses(value = {
                        @ApiResponse(code = 400, message = "Wrong Type Parameter"),
                        @ApiResponse(code = 404, message = "Does not exists User"),
                        @ApiResponse(code = 500, message = "Server Error")})
public class ContentRestService {

    @Autowired
    EventContentRepository eventContentRepository;

    @Autowired
    EventLocationRepository eventLocationRepository;

    @Autowired
    ContentAlarmRepository contentAlarmRepository;

    @Autowired
    ContentThumbUpRepository contentThumbUpRepository;

    @Autowired
    ContentCommentRepository contentCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContentActivityRepository contentActivityRepository;

    @Autowired
    WebCertService webCertService;


    @GetMapping("/findAutoCompliteList/{autoComplteKind}")
    @ResponseBody
    @ApiOperation(value="",
                  notes = "자동완성용 문자 리스트")
    public List<AutoComplite> findAutoCompliteList(@CookieValue(value = "userHash" , defaultValue = "") String userHash
                                                    , @PathVariable("autoComplteKind")  String autoComplteKind) {


        List<AutoComplite> autoComplites = new ArrayList<>();

        autoComplites.add(AutoComplite.builder().autoCompliteText("AAA").autoCompliteKind("K1").build());
        autoComplites.add(AutoComplite.builder().autoCompliteText("BBB").autoCompliteKind("K2").build());
        autoComplites.add(AutoComplite.builder().autoCompliteText("CCC").autoCompliteKind("K3").build());
        return autoComplites;
    }


    @GetMapping("/findEventList/{searchText}/{searchDate}/{latitude}/{longitude}")
    @ResponseBody
    @ApiOperation(value="",  notes = "컨텐츠 통합검색  API")
    @ApiImplicitParams({
                           @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
                           @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
                           @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). " +
                                                                                                                                     "Default sort order is ascending. " +
                                                                                                                                     "Multiple sort criteria are supported.")
                       })
    public Page<EventContentWithUser> findEventList(@CookieValue(value = "webCertInfo" , defaultValue ="") String webCertInfoStr
        , @ApiIgnore @PageableDefault(page=0, size=20) Pageable pageable
        , @PathVariable("searchText")  String searchText
        , @PathVariable("searchDate")  String searchDateStr
        , @PathVariable("latitude")  Long latitude
        , @PathVariable("longitude")  Long longitude ) throws ParseException {

//        http://localhost:8080/Content/V1/findEventList/initSearch/2018-05-18/0/0?page=0
        Date searchDate = null;
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        Page<EventContent> eventContentPage ;

        if (!"모든날짜".equals(searchDateStr)) searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchDateStr);

        if ("initSearch".equals(searchText)) {
            PageRequest defaultPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "eventContentNo")); //현재페이지, 조회할 페이지수, 정렬정보

            if (!"모든날짜".equals(searchDateStr))
                eventContentPage = eventContentRepository.findByStatAndEventStartLessThanEqualAndEventEndGreaterThanEqual(Codes.EV_STAT.S2 , searchDate, searchDate, defaultPageable);
            else
                eventContentPage = eventContentRepository.findByStat(Codes.EV_STAT.S2, defaultPageable);
        }
        else {
            eventContentPage = eventContentRepository.findAllContent(searchText, Codes.EV_STAT.S2.toString(),searchDate, pageable);
        }

        List<EventContent> eventContents = eventContentPage.getContent();

        List<Integer> eventContentNoList = eventContents.stream().map(EventContent::getEventContentNo).collect(Collectors.toList());
        List<Integer> userNos = eventContents.stream().map(EventContent::getCreateNo).collect(Collectors.toList());



        HashMap<Integer, ContentThumbUp> contentThumbUpHashMap = contentThumbUpRepository.findByEventContentNoInAndUseYn(eventContentNoList, "Y")
                                                                                         .stream()
                                                                                         .collect(Collectors.toMap(ContentThumbUp::getEventContentNo,
                                                                                                                   Function.identity(),
                                                                                                                   (o, n) -> o,
                                                                                                                   HashMap::new));
        HashMap<Integer, ContentAlarm> contentAlarmHashMap = contentAlarmRepository.findByEventContentNoInAndUseYn(eventContentNoList, "Y")
                                                                                   .stream()
                                                                                   .collect(Collectors.toMap(ContentAlarm::getEventContentNo,
                                                                                                             Function.identity(),
                                                                                                             (o, n) -> o,
                                                                                                             HashMap::new));
        HashMap<Integer, User> userHashMap = userRepository.findByUserNoIn(userNos)
                                                           .stream().collect(Collectors.toMap(User::getUserNo,
                                                                                              Function.identity(),
                                                                                              (o, n) -> o,
                                                                                              HashMap::new));


        // 1.binding VOs to EventContentWithUser
        // 2.convert  VOs to page Object
        Page<EventContentWithUser> eventContentWithUsers = eventContentPage.map(d -> {
            EventContentWithUser eventContentWithUser = EventContentWithUser.builder()
                                                                            .eventContent(d)
                                                                            .contentThumbUp(Optional.ofNullable(contentThumbUpHashMap.get(d.getEventContentNo()))
                                                                                                    .orElse(ContentThumbUp.builder()
                                                                                                                          .build())
                                                                            )
                                                                            .contentAlarm(Optional.ofNullable(contentAlarmHashMap.get(d.getEventContentNo()))
                                                                                                  .orElse(ContentAlarm.builder()
                                                                                                                      .build())
                                                                            )
                                                                            .user(userHashMap.get(d.getCreateNo()))
                                                                            .commentCnt(contentCommentRepository.countByEventContentNoAndStat(d.getEventContentNo(),Codes.EV_STAT.S2))
                                                                            .isEqualLoginUser(webCertInfo.getUser().getUserNo() == d.getCreateNo())
                                                                            .build();
            //  do location-value each fetch if It can be a lot of value
            eventContentWithUser.getEventContent()
                                .setEventLocations(eventLocationRepository.findByEventContentNoAndUseYn(d.getEventContentNo(),"Y"));
            return eventContentWithUser;
        });

        return eventContentWithUsers;

    }


    @GetMapping("/findContentForContentMain/{eventContentNo}")
    @ResponseBody
    @ApiOperation(value="",  notes = "컨텐츠메인에 사용할 정보 조회 API")
    public EventContentWithUser findContentForContentMain(@CookieValue(value = "webCertInfo" , defaultValue = "") String webCertInfoStr
        , @PathVariable("eventContentNo")  Integer eventContentNo) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        EventContent eventContent = eventContentRepository.findOne(eventContentNo);
        eventContent.setEventLocations(eventLocationRepository.findByEventContentNoAndUseYn(eventContentNo,"Y"));
        ContentThumbUp contentThumbUp = Optional.ofNullable(contentThumbUpRepository
                                                                .findFirstByEventContentNoAndUseYnAndUserNo(eventContentNo,"Y",webCertInfo.getUser().getUserNo()))
                                   .orElse(ContentThumbUp.builder().build());
        ContentAlarm contentAlarm = Optional.ofNullable(contentAlarmRepository
                                                            .findFirstByEventContentNoAndUseYnAndUserNo(eventContentNo,"Y",webCertInfo.getUser().getUserNo()))
                                    .orElse(ContentAlarm.builder().build());


        eventContent.setEventLocations(eventLocationRepository.findByEventContentNoAndUseYn(eventContent.getEventContentNo(), "Y"));

        return EventContentWithUser.builder()
                                   .eventContent(eventContent)
                                   .user(userRepository.findByUserNo(eventContent.getCreateNo()))
                                   .contentThumbUp(contentThumbUp)
                                   .contentAlarm(contentAlarm)
                                   .commentCnt(contentCommentRepository.countByEventContentNoAndStat(eventContentNo, Codes.EV_STAT.S2))
                                   .isEqualLoginUser(webCertInfo.getUser().getUserNo() == eventContent.getCreateNo())
                                   .build();
    }

    @PostMapping(value = "/addContent")
    @ResponseBody
    @ApiOperation(value="",  notes = " 컨텐츠 등록 API")
    @Transactional
    public Integer addContent (@CookieValue("webCertInfo") String webCertInfoStr
                                    , @RequestBody EventContent eventContent) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        eventContent.setUserHash(webCertInfo.getUser().getUserHash());
        eventContent.setCreateDt(toDay);
        eventContent.setCreateNo(webCertInfo.getUser().getUserNo());
        eventContent.setEventDescText(JsoupExtends.text(eventContent.getEventDesc()));
        eventContent.setEventDescThumbnails(JsoupExtends.imagesTagJsonList(eventContent.getEventDesc()));

        EventContent save = eventContentRepository.save(eventContent);


        List<EventLocation> eventLocations = Optional.ofNullable(eventContent.getEventLocations())
                                             .orElse(new ArrayList<>())
                                             .stream().map(d -> {
                                                                    d.setEventContentNo(save.getEventContentNo());
                                                                    d.setCreateDt(toDay);
                                                                    d.setCreateNo(webCertInfo.getUser().getUserNo());
                                                                    return d;
                                                                })
                                             .collect(Collectors.toList());

        List<EventLocation> locations = new ArrayList<>();

        eventLocationRepository.save(eventLocations).forEach(locations::add);
        save.setEventLocations(locations);

        ContentActivity ca = ContentActivity.builder()
                                            .activityRefNo(save.getEventContentNo())
                                            .activityCode(Codes.ACTIVITY_CODE.CONTENT)
                                            .activityStat(Codes.getActivityCode(save.getStat()))
                                            .createDt(toDay)
                                            .createNo(webCertInfo.getUser().getUserNo())
                                            .build();
        contentActivityRepository.save(ca);



        return save.getEventContentNo();
    }

    @PostMapping(value = "/updateContent/{eventContentNo}")
    @ResponseBody
    @ApiOperation(value="",  notes = " 컨텐츠 수정")
    @Transactional
    public Integer updateContent (@CookieValue("webCertInfo") String webCertInfoStr
        , @PathVariable("eventContentNo") Integer eventContentNo
        , @RequestBody EventContent eventContent) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        EventContent findContent = eventContentRepository.findOne(eventContentNo);
        if (!findContent.getCreateNo().equals(webCertInfo.getUser().getUserNo())) throw new APIServerException("it's not same user.");

/*
        save.setUpdateDt(toDay);
        save.setUpdateNo(webCertInfo.getUser().getUserNo());
        save.setTitle(eventContent.getTitle());
        save.setEventDesc(eventContent.getEventDesc());
        save.setTags(eventContent.getTags());
        save.setEventStart(eventContent.getEventStart());
        save.setEventEnd(eventContent.getEventEnd());
        save.setRefPath(eventContent.getRefPath());
        save.setRepeatKind(eventContent.getRepeatKind());
        */

        eventContent.setEventContentNo(findContent.getEventContentNo());
        eventContent.setUserHash(findContent.getUserHash());
        eventContent.setStat(findContent.getStat());
        eventContent.setCreateDt(findContent.getCreateDt());
        eventContent.setCreateNo(findContent.getCreateNo());

        eventContent.setUpdateDt(toDay);
        eventContent.setUpdateNo(webCertInfo.getUser().getUserNo());
        eventContent.setEventDescText(JsoupExtends.text(eventContent.getEventDesc()));
        eventContent.setEventDescThumbnails(JsoupExtends.imagesTagJsonList(eventContent.getEventDesc()));


        eventContentRepository.save(eventContent);

        List<EventLocation> eventLocations = Optional.ofNullable(eventContent.getEventLocations())
                                                     .orElse(new ArrayList<>())
                                                     .stream().map(d -> {
                d.setEventContentNo(findContent.getEventContentNo());
                d.setCreateDt(toDay);
                d.setCreateNo(webCertInfo.getUser().getUserNo());
                return d;
            }).collect(Collectors.toList());

        List<EventLocation> locations = new ArrayList<>();


        eventLocationRepository.updateContentLocationStat(EventLocation.builder()
                                                                       .eventContentNo(eventContent.getEventContentNo())
                                                                       .createNo(webCertInfo.getUser().getUserNo())
                                                                       .useYn("N")
                                                                       .updateNo(webCertInfo.getUser().getUserNo())
                                                                       .updateDt(toDay)
                                                                       .build());

        eventLocationRepository.save(eventLocations).forEach(locations::add);
        findContent.setEventLocations(locations);


        return findContent.getEventContentNo();
    }

    @PatchMapping(value = "/updateContentStat/{eventContentNo}/{stat}")
    @ResponseBody
    @ApiOperation(value="",  notes = " 컨텐츠 수정")
    @Transactional
    public Integer updateContentStat (@CookieValue("webCertInfo") String webCertInfoStr
        ,@PathVariable("eventContentNo") Integer eventContentNo
        ,@PathVariable("stat") Codes.EV_STAT stat
        ) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        eventContentRepository.updateContentStat(EventContent.builder()
                                                        .eventContentNo(eventContentNo)
                                                        .updateDt(toDay)
                                                        .updateNo(webCertInfo.getUser().getUserNo())
                                                        .stat(stat)
                                                        .build());

        contentActivityRepository.updateContentActivityStat(ContentActivity.builder()
                                                      .activityRefNo(eventContentNo)
                                                      .activityCode(Codes.ACTIVITY_CODE.CONTENT)
                                                      .activityStat(Codes.getActivityCode(stat))
                                                      .updateDt(toDay)
                                                      .updateNo(webCertInfo.getUser().getUserNo())
                                                      .build());
        return eventContentNo;
    }




    @PostMapping(value = "/addContentAlarm")
    @ResponseBody
    @ApiOperation(value="",  notes = " 컨텐츠 알람등록 요청 API")
    @Transactional
    public Integer addContentAlarm (@CookieValue("webCertInfo") String webCertInfoStr
        , @RequestBody ContentAlarm contentAlarm) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        contentAlarm.setUserNo(webCertInfo.getUser().getUserNo());
        contentAlarm.setUseYn("Y");
        contentAlarm.setCreateDt(toDay);
        contentAlarm.setCreateNo(webCertInfo.getUser().getUserNo());

        return contentAlarmRepository.save(contentAlarm).getContentAlarmNo();
    }

    @PatchMapping(value = "/updateContentAlarm/{contentAlarmNo}/{useYn}")
    @ApiOperation(value="",  notes = "컨텐츠 알람 수정 요청 API")
    @ResponseBody
    @Transactional
    public Integer UpdateContentAlarm (@CookieValue("webCertInfo") String webCertInfoStr
        , @PathVariable("contentAlarmNo") Integer contentAlarmNo , @PathVariable("useYn") String useYn) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);




        Integer updateCount = contentAlarmRepository.updateContentAlarmStat( ContentAlarm.builder()
                                                                                         .contentAlarmNo(contentAlarmNo)
                                                                                         .updateDt(toDay)
                                                                                         .updateNo(webCertInfo.getUser().getUserNo())
                                                                                         .useYn(useYn)
                                                                                         .build());

        contentActivityRepository.updateContentActivityStat(ContentActivity.builder()
                                                                           .activityRefNo(contentAlarmNo)
                                                                           .activityCode(Codes.ACTIVITY_CODE.ALARM)
                                                                           .activityStat(Codes.getActivityCode(useYn))
                                                                           .updateDt(toDay)
                                                                           .updateNo(webCertInfo.getUser().getUserNo())
                                                                           .build());

        return updateCount;

    }


    @PostMapping(value = "/addContentThumbUp")
    @ApiOperation(value="",  notes = "컨텐츠 따봉 등록 요청 API")
    @ResponseBody
    @Transactional
    public Integer addContentThumbUp(@CookieValue("webCertInfo") String webCertInfoStr
        , @RequestBody ContentThumbUp contentThumbUp) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        contentThumbUp.setUserNo(webCertInfo.getUser().getUserNo());
        contentThumbUp.setUseYn("Y");
        contentThumbUp.setCreateDt(toDay);
        contentThumbUp.setCreateNo(webCertInfo.getUser().getUserNo());

        return contentThumbUpRepository.save(contentThumbUp).getContentThumbupNo();

    }


    @PatchMapping(value = "/updateContentThumbUp/{contentThumbupNo}/{useYn}")
    @ApiOperation(value="",  notes = "컨텐츠 따봉 수정 요청 API")
    @ResponseBody
    @Transactional
    public Integer updateContentThumbUp(@CookieValue("webCertInfo") String webCertInfoStr
        , @PathVariable("contentThumbupNo") Integer contentThumbupNo , @PathVariable("useYn") String useYn) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        Integer updateCount = contentThumbUpRepository.updateContentThumbUpStat(contentThumbupNo, webCertInfo.getUser().getUserNo(), useYn);
        contentActivityRepository.updateContentActivityStat(ContentActivity.builder()
                                                                           .activityRefNo(contentThumbupNo)
                                                                           .activityCode(Codes.ACTIVITY_CODE.THUMBSUP)
                                                                           .activityStat(Codes.getActivityCode(useYn))
                                                                           .updateDt(toDay)
                                                                           .updateNo(webCertInfo.getUser().getUserNo())
                                                                           .build());

        return updateCount;
    }

    @GetMapping("/findCommentList/{eventContentNo}")
    @ResponseBody
    @ApiOperation(value="",  notes = "컨텐츠의 전체댓글 조회  API")
    public List<ContentCommentWithUser> findEventList(@CookieValue(value = "userHash" , defaultValue = "") String userHash
        ,@PathVariable("eventContentNo") Integer eventContentNo
        ) {

        // comment list get
        List<ContentComment> contentComments = contentCommentRepository.findByEventContentNoAndStatOrderByCreateDtDesc(eventContentNo,Codes.EV_STAT.S2);
        List<Integer> userNos = contentComments.stream().map(ContentComment::getUserNo).collect(Collectors.toList());

        // userNo list를 이용해서 사용자 정보 list로 가져와서 map으로 변환
        Map<Integer, User> userMap = userRepository.findByUserNoIn(userNos)
                                          .stream().collect(Collectors.toMap(User::getUserNo,
                                                                             Function.identity(),
                                                                             (o,n) -> o,
                                                                             HashMap::new));

        // 조회한 comment리스트와 user list를 합쳐서 리턴
        return contentComments.stream().map(d -> {
            return ContentCommentWithUser.builder()
                                         .contentComment(d)
                                         .user(userMap.get(d.getUserNo()))
                                         .build();
        }).collect(Collectors.toList());

    }

    @GetMapping("/findOneComment/{contentCommentNo}")
    @ResponseBody
    @ApiOperation(value="",  notes = "컨텐츠의 개별댓글 상세정보 조회  API")
    public ContentCommentWithUser findOneComment(@CookieValue(value = "userHash" , defaultValue = "") String userHash
        ,@PathVariable("contentCommentNo") Integer contentCommentNo)
     {
         ContentComment contentComment = contentCommentRepository.findByContentCommentNoAndStat(contentCommentNo,Codes.EV_STAT.S2);

         // 조회한 comment리스트와 user list를 합쳐서 리턴
        return ContentCommentWithUser.builder()
                                     .contentComment(contentComment)
                                     .user(userRepository.findByUserNo(contentComment.getUserNo()))
                                     .build();
    }





    @PostMapping(value = "/addContentComment")
    @ApiOperation(value="",  notes = "컨텐츠 댓글 등록 요청 API")
    @ResponseBody
    @Transactional
    public Integer addContentComment (@CookieValue(value = "webCertInfo" , defaultValue = "") String webCertInfoStr
        , @RequestBody ContentComment contentComment) {

        Date toDay = new Date();

        if (StringUtils.isEmpty(webCertInfoStr) ) {
            if ( StringUtils.isEmpty(contentComment.getCommentPw())) throw new APIServerException("비회원은 댓글 비밀번호를 필수로 입력 해야 합니다.");

            contentComment.setCommentPw(DigestUtils.sha256Hex(contentComment.getCommentPw()));

            contentComment.setUserNo(-1);
            contentComment.setCreateNo(-1);

        }else{

            WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
            contentComment.setUserNo(webCertInfo.getUser().getUserNo());
            contentComment.setCreateNo(webCertInfo.getUser().getUserNo());
        }

        contentComment.setStat(Codes.EV_STAT.S2);
        contentComment.setCreateDt(toDay);

        return contentCommentRepository.save(contentComment).getContentCommentNo();

    }

    @PatchMapping(value = "/updateContentComment")
    @ApiOperation(value="",  notes = "컨텐츠 댓글 수정 요청 API")
    @ResponseBody
    @Transactional
    public Integer updateContentComment (@CookieValue(value = "webCertInfo") String webCertInfoStr
        , @RequestBody ContentComment contentComment) {


        Date toDay = new Date();



        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        contentComment.setUserNo(webCertInfo.getUser().getUserNo());
        contentComment.setUpdateNo(webCertInfo.getUser().getUserNo());
        contentComment.setUpdateDt(toDay);



        Integer  returnVal =  contentCommentRepository.updateContentComment(contentComment);

        contentActivityRepository.updateContentActivityStat(ContentActivity.builder()
                                                                           .activityRefNo(contentComment.getContentCommentNo())
                                                                           .activityCode(Codes.ACTIVITY_CODE.COMMENT)
                                                                           .activityStat(Codes.getActivityCode(contentComment.getStat()))
                                                                           .updateDt(toDay)
                                                                           .updateNo(webCertInfo.getUser().getUserNo())
                                                                           .build());

        return returnVal;
    }

    @PatchMapping(value = "/deleteContentComment/{contentCommentNo}")
    @ApiOperation(value="",  notes = "컨텐츠 댓글 삭제 요청 API")
    @ResponseBody
    @Transactional
    public Integer deleteContentComment (@CookieValue(value = "webCertInfo") String webCertInfoStr
        , @PathVariable("contentCommentNo") Integer contentCommentNo) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        Integer updateCount = contentCommentRepository.updateContentCommentStat(ContentComment.builder()
                                                                                          .stat(Codes.EV_STAT.S4)
                                                                                          .userNo(webCertInfo.getUser().getUserNo())
                                                                                          .contentCommentNo(contentCommentNo)
                                                                                          .updateDt(toDay)
                                                                                          .updateNo(webCertInfo.getUser().getUserNo())
                                                                                          .build());
        contentActivityRepository.updateContentActivityStat(ContentActivity.builder()
                                                                           .activityRefNo(contentCommentNo)
                                                                           .activityCode(Codes.ACTIVITY_CODE.COMMENT)
                                                                           .activityStat(Codes.getActivityCode(Codes.EV_STAT.S4))
                                                                           .updateDt(toDay)
                                                                           .updateNo(webCertInfo.getUser().getUserNo())
                                                                           .build());

        return updateCount;
    }


    @GetMapping("/findContentActivityList/{activityCode}")
    @ResponseBody
    @ApiOperation(value="",  notes = "사용자 활동내역에 대한 조회 API")
    @ApiImplicitParams({
                           @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
                           @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
                           @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). " +
                               "Default sort order is ascending. " +
                               "Multiple sort criteria are supported.")
                       })
    public Page<ContentActivityComb> findContentActivityList(@CookieValue(value = "webCertInfo" , defaultValue ="") String webCertInfoStr
        , @ApiIgnore @PageableDefault(page=0, size=20) Pageable pageableParam
        , @PathVariable("activityCode") Codes.ACTIVITY_CODE activityCode
        ) throws ParseException {

        PageRequest pageable = new PageRequest(pageableParam.getPageNumber(), pageableParam.getPageSize(), new Sort(Sort.Direction.DESC, "contentActivityNo")); //현재페이지, 조회할 페이지수, 정렬정보
        Codes.ACTIVITY_STAT[] stats = new Codes.ACTIVITY_STAT[] {Codes.ACTIVITY_STAT.S2 , Codes.ACTIVITY_STAT.Y};
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        Page<ContentActivity> activityList = null;

        if (activityCode.equals(Codes.ACTIVITY_CODE.ALL))
            activityList = contentActivityRepository.findByCreateNoAndActivityStatIn(webCertInfo.getUser().getUserNo(), stats, pageable);
        else
            activityList = contentActivityRepository.findByCreateNoAndActivityStatInAndActivityCode(webCertInfo.getUser().getUserNo(), stats,activityCode, pageable);


        Page<ContentActivityComb> contentActivityCombs = activityList.map(d -> {
                                                             ContentActivityComb cac = ContentActivityComb.builder()
                                                                                                          .contentActivity(d)
                                                                                                          .build();


            log.debug("########### activityList ");
            log.debug(cac.getContentActivity().getActivityCode().toString());
            log.debug(String.valueOf(d.getActivityRefNo()));


             switch (cac.getContentActivity().getActivityCode())
             {
                 case CONTENT:
                     cac.setEventContent(eventContentRepository.findOne(d.getActivityRefNo()));
                     break;
                 case COMMENT:
                     cac.setEventContent(eventContentRepository.findOne(contentCommentRepository.findOne(d.getActivityRefNo()).getEventContentNo()));
                     break;
                 case ALARM:
                     cac.setEventContent(eventContentRepository.findOne(contentAlarmRepository.findOne(d.getActivityRefNo()).getEventContentNo()));
                     break;
                 case THUMBSUP:
                     cac.setEventContent(eventContentRepository.findOne(contentThumbUpRepository.findOne(d.getActivityRefNo()).getEventContentNo()));
                     break;
             }
             return cac;
         }
        );
        return contentActivityCombs;
    }

}
