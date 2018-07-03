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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import somun.common.util.DateUtils;
import somun.service.EventContentService;
import somun.service.auth.WebCertService;
import somun.service.repository.content.ContentActivityModifyRepository;
import somun.service.repository.content.ContentActivityRepository;
import somun.service.repository.content.ContentAlarmModifyRepository;
import somun.service.repository.content.ContentAlarmRepository;
import somun.service.repository.content.ContentCommentModifyRepository;
import somun.service.repository.content.ContentCommentRepository;
import somun.service.repository.content.ContentThumbUpModifyRepository;
import somun.service.repository.content.ContentThumbUpRepository;
import somun.service.repository.content.EventContentModifyRepository;
import somun.service.repository.content.EventContentRepository;
import somun.service.repository.content.EventLocation;
import somun.service.repository.content.EventLocationModifyRepository;
import somun.service.repository.content.EventLocationRepository;
import somun.service.repository.user.UserRepository;
import somun.service.repository.vo.ContentActivityComb;
import somun.service.repository.vo.ContentCommentWithUser;
import somun.service.repository.vo.EventContentWithUser;
import somun.service.repository.vo.WebCertInfo;
import somun.service.repository.vo.content.AutoComplite;
import somun.service.repository.vo.content.ContentActivity;
import somun.service.repository.vo.content.ContentAlarm;
import somun.service.repository.vo.content.ContentComment;
import somun.service.repository.vo.content.ContentThumbUp;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.function.SearchIndexComb;
import somun.service.repository.vo.user.User;
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
    EventContentModifyRepository eventContentModifyRepository;

    @Autowired
    EventLocationRepository eventLocationRepository;
    @Autowired
    EventLocationModifyRepository eventLocationModifyRepository;


    @Autowired
    ContentAlarmRepository contentAlarmRepository;
    @Autowired
    ContentAlarmModifyRepository contentAlarmModifyRepository;

    @Autowired
    ContentThumbUpRepository contentThumbUpRepository;
    @Autowired
    ContentThumbUpModifyRepository contentThumbUpModifyRepository;


    @Autowired
    ContentCommentRepository contentCommentRepository;
    @Autowired
    ContentCommentModifyRepository contentCommentModifyRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    ContentActivityRepository contentActivityRepository;
    @Autowired
    ContentActivityModifyRepository contentActivityModifyRepository;


    @Autowired
    WebCertService webCertService;

    @Autowired
    EventContentService eventContentService;


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

    @GetMapping("/findEventList/{searchText}/{searchDate}/{longitude}/{latitude}")
    @ResponseBody
    @ApiOperation(value="",  notes = "컨텐츠 통합검색  API")
    @ApiImplicitParams({
                           @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
                           @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
                           @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). " +
                                                                                                                                     "Default sort order is ascending. " + "Multiple sort criteria are supported.")
                       })
    public Page<EventContentWithUser> findEventList(@CookieValue(value = "webCertInfo" , defaultValue ="") String webCertInfoStr
        , @ApiIgnore @PageableDefault(page=0, size=20) Pageable pageable
        , @PathVariable("searchText")  String searchText
        , @PathVariable("searchDate")  String searchDateStr
        , @PathVariable("longitude")  Long longitude
        , @PathVariable("latitude")  Long latitude
         ) throws ParseException {

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
                eventContentPage = eventContentRepository.findByStatAndEventEndGreaterThanEqual(Codes.EV_STAT.S2, defaultPageable, DateUtils.addDayDate("yyyyMMdd", 0));
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
        eventContent.setUpdateDt(toDay);
        eventContent.setUpdateNo(webCertInfo.getUser().getUserNo());


        return eventContentService.saveEventContent(eventContent).getEventContentNo();
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

        eventContent.setUpdateDt(toDay);
        eventContent.setUpdateNo(webCertInfo.getUser().getUserNo());

        return eventContentService.updateEventContent(eventContentNo, eventContent).getEventContentNo();
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

        eventContentModifyRepository.updateContentStat(EventContent.builder()
                                                        .eventContentNo(eventContentNo)
                                                        .updateDt(toDay)
                                                        .updateNo(webCertInfo.getUser().getUserNo())
                                                        .stat(stat)
                                                        .build());

        contentActivityModifyRepository.updateContentActivityStat(ContentActivity.builder()
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
        contentAlarm.setUpdateDt(toDay);
        contentAlarm.setUpdateNo(webCertInfo.getUser().getUserNo());

        Integer contentAlarmNo = contentAlarmModifyRepository.save(contentAlarm).getContentAlarmNo();

        contentActivityModifyRepository.save(ContentActivity.builder()
                                                            .activityRefNo(contentAlarmNo)
                                                            .activityCode(Codes.ACTIVITY_CODE.ALARM)
                                                            .activityStat(Codes.getActivityCode("Y"))
                                                            .createDt(contentAlarm.getUpdateDt())
                                                            .createNo(contentAlarm.getUpdateNo())
                                                            .updateDt(contentAlarm.getUpdateDt())
                                                            .updateNo(contentAlarm.getUpdateNo())
                                                            .build());

        return contentAlarmNo;
    }

    @PatchMapping(value = "/updateContentAlarm/{contentAlarmNo}/{useYn}")
    @ApiOperation(value="",  notes = "컨텐츠 알람 수정 요청 API")
    @ResponseBody
    @Transactional
    public Integer UpdateContentAlarm (@CookieValue("webCertInfo") String webCertInfoStr
        , @PathVariable("contentAlarmNo") Integer contentAlarmNo , @PathVariable("useYn") String useYn) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);




        Integer updateCount = contentAlarmModifyRepository.updateContentAlarmStat( ContentAlarm.builder()
                                                                                         .contentAlarmNo(contentAlarmNo)
                                                                                         .updateDt(toDay)
                                                                                         .updateNo(webCertInfo.getUser().getUserNo())
                                                                                         .useYn(useYn)
                                                                                         .build());

        contentActivityModifyRepository.updateContentActivityStat(ContentActivity.builder()
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
        contentThumbUp.setUpdateDt(toDay);
        contentThumbUp.setUpdateNo(webCertInfo.getUser().getUserNo());

        Integer contentThumbup = contentThumbUpModifyRepository.save(contentThumbUp).getContentThumbupNo();

        contentActivityModifyRepository.save(ContentActivity.builder()
                                                            .activityRefNo(contentThumbup)
                                                            .activityCode(Codes.ACTIVITY_CODE.THUMBSUP)
                                                            .activityStat(Codes.getActivityCode("Y"))
                                                            .createDt(contentThumbUp.getUpdateDt())
                                                            .createNo(contentThumbUp.getUpdateNo())
                                                            .build());


        return contentThumbup;

    }


    @PatchMapping(value = "/updateContentThumbUp/{contentThumbupNo}/{useYn}")
    @ApiOperation(value="",  notes = "컨텐츠 따봉 수정 요청 API")
    @ResponseBody
    @Transactional
    public Integer updateContentThumbUp(@CookieValue("webCertInfo") String webCertInfoStr
        , @PathVariable("contentThumbupNo") Integer contentThumbupNo , @PathVariable("useYn") String useYn) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        Integer updateCount = contentThumbUpModifyRepository.updateContentThumbUpStat(contentThumbupNo, webCertInfo.getUser().getUserNo(), useYn);
        contentActivityModifyRepository.updateContentActivityStat(ContentActivity.builder()
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

//        userNos.parallelStream().map(d->{});

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
    public Integer addContentComment (@CookieValue(value = "webCertInfo") String webCertInfoStr
        , @RequestBody ContentComment contentComment) {

        Date toDay = new Date();


        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        contentComment.setUserNo(webCertInfo.getUser().getUserNo());
        contentComment.setStat(Codes.EV_STAT.S2);
        contentComment.setCreateNo(webCertInfo.getUser().getUserNo());
        contentComment.setCreateDt(toDay);
        contentComment.setUpdateDt(toDay);
        contentComment.setUpdateNo(webCertInfo.getUser().getUserNo());

        Integer contentCommentNo = contentCommentModifyRepository.save(contentComment).getContentCommentNo();

        contentActivityModifyRepository.save(ContentActivity.builder()
                                                            .activityRefNo(contentCommentNo)
                                                            .activityCode(Codes.ACTIVITY_CODE.COMMENT)
                                                            .activityStat(Codes.getActivityCode(Codes.EV_STAT.S2))
                                                            .createDt(contentComment.getUpdateDt())
                                                            .createNo(contentComment.getUpdateNo())
                                                            .updateDt(contentComment.getUpdateDt())
                                                            .updateNo(contentComment.getUpdateNo())
                                                            .build());


        return contentCommentNo;

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
        contentComment.setStat(Codes.EV_STAT.S2);
        contentComment.setUpdateDt(toDay);

        Integer  returnVal =  contentCommentModifyRepository.updateContentComment(contentComment);

        contentActivityModifyRepository.updateContentActivityStat(ContentActivity.builder()
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
        Integer updateCount = contentCommentModifyRepository.updateContentCommentStat(ContentComment.builder()
                                                                                          .stat(Codes.EV_STAT.S4)
                                                                                          .userNo(webCertInfo.getUser().getUserNo())
                                                                                          .contentCommentNo(contentCommentNo)
                                                                                          .updateDt(toDay)
                                                                                          .updateNo(webCertInfo.getUser().getUserNo())
                                                                                          .build());

        contentActivityModifyRepository.updateContentActivityStat(ContentActivity.builder()
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


    @GetMapping("/indexDocList/{indexStartDate}/{indexEndDate}")
    @ResponseBody
    @ApiOperation(value="",  notes = "검색 색인 생성  API")
    @ApiImplicitParams({
                           @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
                           @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
                           @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). " +
                               "Default sort order is ascending. " +
                               "Multiple sort criteria are supported.")
                       })
    public Page<SearchIndexComb> indexDocList(@ApiIgnore @PageableDefault(page=0, size=10) Pageable pageable
        , @PathVariable("indexStartDate")  String indexStartDate
        , @PathVariable("indexEndDate")  String indexEndDate
         ) throws ParseException {


        Page<EventContent> indexDocs = eventContentRepository
            .findAllByUpdateDtGreaterThanEqualAndUpdateDtLessThanEqualAndStat(
                new SimpleDateFormat("yyyy-MM-dd").parse(indexStartDate)
                , new SimpleDateFormat("yyyy-MM-dd").parse(indexEndDate)
                , Codes.EV_STAT.S2, pageable
            );


        List<EventContent> eventContents = indexDocs.getContent();

        List<Integer> eventContentNoList = eventContents.stream().map(EventContent::getEventContentNo).collect(Collectors.toList());

        // 1.binding VOs to EventContentWithUser
        // 2.convert  VOs to page Object
        Page<SearchIndexComb> searchIndexCombPage = indexDocs.map(d -> {
            //  do location-value each fetch if It can be a lot of value
            EventLocation eventLocation = Optional.ofNullable(eventLocationRepository.findFirstByUseYnAndEventContentNo("Y", d.getEventContentNo()))
                                                    .orElse(EventLocation.builder().build());

            SearchIndexComb searchIndexComb = SearchIndexComb.builder()
                                                            .eventContentNo(d.getEventContentNo())
                                                            .title(d.getTitle())
                                                            .eventDescText(d.getEventDescText())
                                                            .eventStart(d.getEventStart())
                                                            .eventEnd(d.getEventEnd())
                                                            .tags(d.getTags())
                                                            .longitude(eventLocation.getLongitude())
                                                            .latitude(eventLocation.getLatitude())
                                                             .build();

            return searchIndexComb;
        });

        return searchIndexCombPage;

    }

}
