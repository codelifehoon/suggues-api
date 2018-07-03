package somun.api.v1.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.util.LogUtil;
import somun.common.util.RandomUti;
import somun.service.repository.content.EventLocation;
import somun.service.repository.vo.ContentCommentWithUser;
import somun.service.repository.vo.EventContentWithUser;
import somun.service.repository.vo.content.AutoComplite;
import somun.service.repository.vo.content.ContentAlarm;
import somun.service.repository.vo.content.ContentComment;
import somun.service.repository.vo.content.ContentThumbUp;
import somun.service.repository.vo.content.EventContent;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ContentRestServiceTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    String htmlSample;




    HttpHeaders requestHeaders;

    @Before
    public void setUp() throws Exception {
        requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "webCertInfo=j%3A%7B%22userHash%22%3A%2221474836247%22%7D");
        htmlSample = "<p style=\"text-align:left;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<p style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: inherit;font-family: 나눔고딕, " +
            "nanumgothic, se_NanumGothic, AppleSDGothicNeo, sans-serif, simhei;\">혹등고래인 '모모'가 자신의 삶의 의미를 찾아 떠나 최종적으로는 강에 도달하는 모험을 담은 책입니다" +
            ".</span><br>&nbsp;</p>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p style=\"text-align:center;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<img src=\"https://postfiles.pstatic.net/MjAxODA1MDRfMTMg/MDAxNTI1MzY5MzQ3MTg5.gfv-selr_JH-0ZaeOAqYIexAbAsbOoVhYspy-moggX0g" +
            ".PCKK51zrOMN63482IjQBIUjT-ipyv9gB0PZTUheAossg.JPEG.fslin_/1.jpg?type=w773\" alt=\"\" style=\"float:none;height: auto;width: " +
            "600px\"/>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<p style=\"text-align:center;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<blockquote style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: 12px;font-family: " +
            "나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">보통의 일반적인 삶에 의문을 품는다는 것.</span><br><span style=\"color: inherit;" +
            "background-color: initial;font-size: 12px;font-family: 나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">고래들에겐 당연한 일들이 " +
            "모모에겐 고통스럽기만 하다.</span></blockquote>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<p style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: inherit;font-family: 나눔고딕, " +
            "nanumgothic, se_NanumGothic, AppleSDGothicNeo, sans-serif, simhei;\"> 모모는 특이합니다. 왜냐면 보통의 고래들과는 다른 생각을 갖고 살아가기 때문입니다. 집단 사냥으로 먹이를" +
            " 사냥하고, 많은 암컷들과 교미하고 번식하는 일. 혹등고래의 '일반적인 삶'에는 모모는 큰 관심이 없습니다. 오히려, 그 일반적인 삶에 대해 끝도 없이 의문을 던지고 자신의 삶의 의미를 찾습니다. 모모는 노래하는 것, 그리고 " +
            "자신과 대화를 나누는 것을 좋아합니다. 많은 고래들은 모모를 멸시하고 그의 삶을 꾸짖습니다.</span><br></p>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p style=\"text-align:justify;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<img src=\"https://postfiles.pstatic.net/MjAxODA1MDRfNDQg/MDAxNTI1MzY5NjQwMDU0.DFaHxP7ogSpDnVoSyWclsnxK1mcAgxHDIfYRbMu9pYYg" +
            ".gMny8cvJhPrlnPgxYEQ2tCUlyqGlmxBzrSwyjV_BLBIg.JPEG.fslin_/1.jpg?type=w773\" alt=\"\" style=\"float:none;height: auto;width: " +
            "693px\"/>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<p style=\"text-align:center;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<blockquote style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: 12px;font-family: " +
            "나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">싸우는 것도 싫어하고,</span><br><span style=\"color: inherit;background-color: " +
            "initial;font-size: 12px;font-family: 나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">사는 것도 재미없다고 하고, …</span><br><span " +
            "style=\"color: inherit;background-color: initial;font-size: 12px;font-family: 나눔명조, nanummyeongjo, se_NanumMyeongjo, serif, " +
            "simsun;\">대체 네가 원하는 게 뭐야?</span><br><span style=\"color: inherit;background-color: initial;font-size: 12px;font-family: 나눔명조, " +
            "nanummyeongjo, se_NanumMyeongjo, serif, simsun;\">-강을 눈앞에 두고 두려워하는 바야바가 모모에게</span></blockquote>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<p style=\"text-align:left;\"></p>\n" +
            "<p></p>\n" +
            "<p></p>\n" +
            "<p style=\"text-align:inherit;\"><span style=\"color: inherit;background-color: initial;font-size: inherit;font-family: 나눔고딕, " +
            "nanumgothic, se_NanumGothic, AppleSDGothicNeo, sans-serif, simhei;\"> 모모는 용감하고 모험감 넘치는 친구 '바야바'를 만납니다. 그리고, 어떤 고래도 하지 않았던 도전을 " +
            "합니다. 바로 강을 역류해서 늪으로 가는 위험천만한 여행을 계획합니다. 하지만, 낯선 강의 풍경에 두려움을 느낀 모모는 강을 눈앞에 두고 깊은 생각에 잠깁니다. 모모는 강을 역류해 올라갈 수 있을까요? 또 이후에 어떻게 " +
            "될까요?</span></p>\n" +
            "<p></p>\n" +
            "<p><span style=\"color: rgb(128,128,128);background-color: rgb(255,255,255);font-size: 12px;font-family: Dotum;" +
            "\"><strong>[출처]</strong></span> <a href=\"https://blog.naver.com/fslin_/221267730862\" target=\"_blank\"><span style=\"color: " +
            "rgb(96,140,186);background-color: rgb(255,255,255);font-size: 12px;font-family: Dotum;\">[책 리뷰] 삶의 목표를 찾아서, 혹등고래 모모의 " +
            "여행</span></a><span style=\"color: rgb(128,128,128);background-color: rgb(255,255,255);font-size: 12px;font-family: Dotum;" +
            "\">|<strong>작성자</strong></span> <a href=\"https://blog.naver.com/fslin_\" target=\"_blank\"><span style=\"color: rgb(96,140,186)" +
            ";background-color: rgb(255,255,255);font-size: 12px;font-family: Dotum;\">성또치</span></a></p>\n" +
            "<p><br></p>\n";
    }

    @Test
    public void findAutoCompliteList(){

        AutoComplite[] autoCompliteList = this.testRestTemplate
                .getForObject("/Content/V1/findAutoCompliteList/autoComplteKind", AutoComplite[].class);


        assertThat(autoCompliteList).isNotNull();

    }


    @Test
    public  void findEventList(){

        String reqUrl = "/Content/V1/findEventList"
            +"/혹등고래인"
            +"/2018-05-20"
            +"/1"
            +"/2";

        String content = this.testRestTemplate
            .getForObject(reqUrl, String.class);


        log.debug(content);

        assertThat(content).isNotNull();


    }

    @Test
    public void addContent () {

        List<EventLocation> eventLocations = new ArrayList<>();

        eventLocations.add(EventLocation.builder().latitude(1)
                                        .longitude(2)
                                        .address(RandomUti.randomString(9))
                                        .addressDtls(RandomUti.randomString(9))
                                        .useYn("Y")
                                        .build()
        );

        eventLocations.add(EventLocation.builder().latitude(3.123456789)
                                        .longitude(4.123456789)
                                        .address(RandomUti.randomString(9))
                                        .addressDtls(RandomUti.randomString(9))
                                        .useYn("Y")
                                        .build()
        );

        EventContent eventContent = EventContent.builder().userHash(RandomUti.randomString(9))
                                                .title(RandomUti.randomString(9))
                                                .eventDesc(RandomUti.randomString(100))
                                                .eventStart(new Date())
                                                .eventEnd(new Date())
                                                .repeatKind(Codes.EV_REPKIND.M1)
                                                .refPath(RandomUti.randomString(3))
                                                .eventLocations(eventLocations)
                                                .tags(RandomUti.randomString(3))
                                                .stat(Codes.EV_STAT.S2)
                                                .build();
        Integer content = testRestTemplate.exchange("/Content/V1/addContent", HttpMethod.POST
            , new HttpEntity(eventContent, requestHeaders)
            , Integer.class).getBody();

        assertThat(content).isGreaterThan(0);
    }

    @Test
    public void updateContent () {

        List<EventLocation> eventLocations = new ArrayList<>();

        eventLocations.add(EventLocation.builder().latitude(1)
                                        .longitude(2)
                                        .address(RandomUti.randomString(9))
                                        .addressDtls(RandomUti.randomString(9))
                                        .useYn("Y")
                                        .build()
        );

        EventContent eventContent = EventContent.builder().userHash(RandomUti.randomString(9))
                                                .title(RandomUti.randomString(9))
                                                .eventDesc(RandomUti.randomString(100))
                                                .eventStart(new Date())
                                                .eventEnd(new Date())
                                                .repeatKind(Codes.EV_REPKIND.M1)
                                                .refPath(RandomUti.randomString(3))
                                                .eventLocations(eventLocations)
                                                .tags(RandomUti.randomString(3))
                                                .stat(Codes.EV_STAT.S2)
                                                .build();
        Integer content = testRestTemplate.exchange("/Content/V1/updateContent/105", HttpMethod.POST
            , new HttpEntity(eventContent, requestHeaders)
            , Integer.class).getBody();
        new LogUtil().printObject(content);
        assertThat(content).isGreaterThan(0);
    }



    @Test
    public void updateContentStat () {

        Integer content = testRestTemplate.exchange("/Content/V1/updateContentStat/108/" + Codes.EV_STAT.S4, HttpMethod.PATCH
            , new HttpEntity(null, requestHeaders)
            , Integer.class).getBody();
        new LogUtil().printObject(content);
        assertThat(content).isGreaterThan(0);
    }


    @Test
    public void addContentSimple () {


        EventContent eventContent = EventContent.builder().userHash(RandomUti.randomString(9))
                                                .title(RandomUti.randomString(9))
                                                .eventDesc(RandomUti.randomString(9))
                                                .eventStart(new Date())
                                                .eventEnd(new Date())
                                                .repeatKind(Codes.EV_REPKIND.M1)
                                                .refPath(RandomUti.randomString(3))
                                                .tags(RandomUti.randomString(3))
                                                .stat(Codes.EV_STAT.S2)
                                                .build();
        Integer content = testRestTemplate.exchange("/Content/V1/addContent", HttpMethod.POST
            , new HttpEntity(eventContent, requestHeaders)
            , Integer.class).getBody();
        new LogUtil().printObject(content);
        assertThat(content).isGreaterThan(0);
    }


    @Test
    public void addContent_Not_UserHash () {
        //인증 안되고 게시물 등록하는 경우는 오류를 뱉기에 별도의 테스트 하지않음
    }

    @Test
    public void addContentAlarm () {

        ContentAlarm contentAlarm = ContentAlarm.builder().eventContentNo(85).build();
        Integer resutl = testRestTemplate.exchange("/Content/V1/addContentAlarm", HttpMethod.POST
            , new HttpEntity(contentAlarm, requestHeaders)
            , Integer.class)
                                         .getBody();
        new LogUtil().printObject(resutl);
        assertThat(resutl).isGreaterThan(0);
    }

    @Test
    public void updateContentAlarm () {

        Integer result = testRestTemplate.exchange("/Content/V1/updateContentAlarm/2/N", HttpMethod.PATCH
            , new HttpEntity(null, requestHeaders)
            , Integer.class).getBody();
        new LogUtil().printObject(result);
        assertThat(result).isGreaterThan(0);
    }





    @Test
    public void addContentThumbUp () {
        ContentThumbUp contentThumbUp = ContentThumbUp.builder().eventContentNo(85).build();
        Integer result = testRestTemplate.exchange("/Content/V1/addContentThumbUp", HttpMethod.POST
            , new HttpEntity(contentThumbUp, requestHeaders)
            , Integer.class)
                                         .getBody();
        new LogUtil().printObject(result);
        assertThat(result).isGreaterThan(0);
    }


    @Test
    public void updateContentThumbUp_do_update  () {

        Integer result = testRestTemplate.exchange("/Content/V1/updateContentThumbUp/13/N", HttpMethod.PATCH
            , new HttpEntity(null, requestHeaders)
            , Integer.class)
                                         .getBody();
        new LogUtil().printObject(result);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void updateContentThumbUp_not_update  () {

        Integer result = testRestTemplate.exchange("/Content/V1/updateContentThumbUp/0/N", HttpMethod.PATCH
            , new HttpEntity(null, requestHeaders)
            , Integer.class)
                                         .getBody();
        new LogUtil().printObject(result);
        assertThat(result).isEqualTo(0);
    }


    @Test
    public void addContentComment_member () {
        ContentComment build = ContentComment.builder().commentDesc(RandomUti.randomString(10))
                                             .eventContentNo(85)
                                             .stat(Codes.EV_STAT.S2)
                                             .build();
        Integer body = testRestTemplate.exchange("/Content/V1/addContentComment", HttpMethod.POST
            , new HttpEntity(build, requestHeaders)
            , Integer.class)
                                       .getBody();
        new LogUtil().printObject(body);
        assertThat(body).isGreaterThan(0);
    }

    @Test
    public void addContentComment_not_member () {
        ContentComment build = ContentComment.builder().commentDesc(RandomUti.randomString(10))
                                             .eventContentNo(85)
                                             .commentPw("1234")
                                             .stat(Codes.EV_STAT.S2)
                                             .build();
        Integer body = testRestTemplate.exchange("/Content/V1/addContentComment", HttpMethod.POST
            , new HttpEntity(build)
            , Integer.class)
                                       .getBody();
        new LogUtil().printObject(body);
        assertThat(body).isGreaterThan(0);
    }


    @Test
    public void UpdateContentComment_member () {
        ContentComment build = ContentComment.builder().commentDesc("업데이트"+RandomUti.randomString(10))
                                             .contentCommentNo(2)
                                             .stat(Codes.EV_STAT.S5)
                                             .build();
        Integer body = testRestTemplate.exchange("/Content/V1/updateContentComment", HttpMethod.PATCH
            , new HttpEntity(build, requestHeaders)
            , Integer.class)
                                       .getBody();
        new LogUtil().printObject(body);
        assertThat(body).isGreaterThan(0);
    }





    @Test
    public void findCommentList()
    {
        ContentCommentWithUser[] body = testRestTemplate.exchange("/Content/V1/findCommentList/85"
            , HttpMethod.GET
            , null
            , ContentCommentWithUser[].class)
                                                        .getBody();
        new LogUtil().printObject(body);
        assertThat(body).isNotNull();
    }


    @Test
    public void findContentForContentMain(){
        EventContentWithUser body = testRestTemplate.exchange("/Content/V1/findContentForContentMain/105"
            , HttpMethod.GET
            , null
            , EventContentWithUser.class)
                                                    .getBody();
        new LogUtil().printObject(body);
        assertThat(body.getEventContent().getEventContentNo()).isEqualTo(105);

    }

    @Test
    public void deleteContentComment(){
        Integer body = testRestTemplate.exchange("/Content/V1/deleteContentComment/70"
            , HttpMethod.PATCH
            , new HttpEntity(null, requestHeaders)
            , Integer.class)
                                       .getBody();
        new LogUtil().printObject(body);
        assertThat(body).isNotNull();

    }

    @Test
    public void findContentActivityList() {

        String json = testRestTemplate.exchange("/Content/V1/findContentActivityList/ALL?page=6&size=3", HttpMethod.GET
            , new HttpEntity(null,requestHeaders)
            , String.class).getBody();


        assertThat(json).isNotEmpty();

        log.debug("###############################");
        log.debug(json);


    }

}
