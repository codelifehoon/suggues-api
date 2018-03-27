package somun.api.v1.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.exception.APIServerException;
import somun.service.auth.WebCertService;
import somun.service.repository.AutoComplite;
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
import somun.service.repositoryComb.WebCertInfo;
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


    @GetMapping("/findContentList/{searchText}/{latitude}/{longitude}")
    @ResponseBody
    @ApiOperation(value="",  notes = "이벤트 조회  API")
    public List<EventContent> findEventList(@CookieValue(value = "userHash" , defaultValue = "") String userHash
                                                    , @PathVariable("searchText")  String searchText
                                                    , @PathVariable("latitude")  Long latitude
                                                    , @PathVariable("longitude")  Long longitude ) {

        List<EventContent> eventContents = new ArrayList<>();
        List<EventLocation>  eventLocations = new ArrayList<>();

        eventContentRepository.findAll().forEach(eventContents::add);

        for(EventContent eventContent : eventContents){
            eventContent.setEventLocations(eventLocationRepository.findByEventContentNo(eventContent.getEventContentNo()));
        }

        return eventContents;
    }


    @PostMapping(value = "/AddContent")
    @ResponseBody
    @ApiOperation(value="",  notes = " 이벤트 등록 API")
    @Transactional
    public Integer addContent (@CookieValue("webCertInfo") String webCertInfoStr
                                    , @RequestBody EventContent eventContent) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);



        eventContent.setUserHash(webCertInfo.getUser().getUserHash());
        eventContent.setCreateDt(toDay);
        eventContent.setCreateNo(webCertInfo.getUser().getUserNo());

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

        return save.getEventContentNo();
    }


    @PostMapping(value = "/AddContentAlarm")
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

    @PatchMapping(value = "/UpdateContentAlarm/{contentAlarmNo}/{useYn}")
    @ApiOperation(value="",  notes = "컨텐츠 알람 수정 요청 API")
    @ResponseBody
    @Transactional
    public Integer UpdateContentAlarm (@CookieValue("webCertInfo") String webCertInfoStr
        , @PathVariable("contentAlarmNo") Integer contentAlarmNo , @PathVariable("useYn") String useYn) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        return contentAlarmRepository.updateContentAlarmStat(contentAlarmNo, webCertInfo.getUser().getUserNo(), useYn);

    }


    @PostMapping(value = "/AddContentThumbUp")
    @ApiOperation(value="",  notes = "컨텐츠 따봉 등록 요청 API")
    @ResponseBody
    @Transactional
    public Integer AddContentThumbUp (@CookieValue("webCertInfo") String webCertInfoStr
        , @RequestBody ContentThumbUp contentThumbUp) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);

        contentThumbUp.setUserNo(webCertInfo.getUser().getUserNo());
        contentThumbUp.setUseYn("Y");
        contentThumbUp.setCreateDt(toDay);
        contentThumbUp.setCreateNo(webCertInfo.getUser().getUserNo());

        return contentThumbUpRepository.save(contentThumbUp).getContentThumbupNo();

    }


    @PatchMapping(value = "/UpdateContentThumbUp/{contentThumbupNo}/{useYn}")
    @ApiOperation(value="",  notes = "컨텐츠 따봉 수정 요청 API")
    @ResponseBody
    @Transactional
    public Integer UpdateContentThumbUp (@CookieValue("webCertInfo") String webCertInfoStr
        , @PathVariable("contentThumbupNo") Integer contentThumbupNo , @PathVariable("useYn") String useYn) {

        Date toDay = new Date();
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        return contentThumbUpRepository.updateContentThumbUpStat(contentThumbupNo, webCertInfo.getUser().getUserNo(), useYn);

    }

    @PostMapping(value = "/AddContentComment")
    @ApiOperation(value="",  notes = "컨텐츠 댓글 등록 요청 API")
    @ResponseBody
    @Transactional
    public Integer AddContentComment (@CookieValue(value = "webCertInfo" , defaultValue = "") String webCertInfoStr
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

    @PostMapping(value = "/UpdateContentComment")
    @ApiOperation(value="",  notes = "컨텐츠 댓글 수정 요청 API")
    @ResponseBody
    @Transactional
    public Integer UpdateContentComment (@CookieValue(value = "webCertInfo" , defaultValue = "") String webCertInfoStr
        , @RequestBody ContentComment contentComment) {

        Integer returnVal = null;
        Date toDay = new Date();

        if (StringUtils.isEmpty(webCertInfoStr)) {
            if ( StringUtils.isEmpty(contentComment.getCommentPw())) throw new APIServerException("비회원은 댓글 비밀번호를 필수로 입력 해야 합니다.");
            contentComment.setCommentPw(DigestUtils.sha256Hex(contentComment.getCommentPw()));
            contentComment.setUpdateNo(-1);

        }else{

            WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
            contentComment.setUserNo(webCertInfo.getUser().getUserNo());
            contentComment.setUpdateNo(webCertInfo.getUser().getUserNo());
        }

        contentComment.setUpdateDt(toDay);


        if (StringUtils.isEmpty(webCertInfoStr)) {
            returnVal =  contentCommentRepository.updateContentCommentNotMember(contentComment);
        }else{
            returnVal =  contentCommentRepository.updateContentCommentMember(contentComment);
        }

        return returnVal;
    }



}
