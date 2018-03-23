package somun.api.v1.content;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.util.LogUtil;
import somun.common.util.RandomUti;
import somun.service.repository.AutoComplite;
import somun.service.repository.ContentAlarm;
import somun.service.repository.EventContent;
import somun.service.repository.EventLocation;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ContentRestServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;



    HttpHeaders requestHeaders;

    @Before
    public void setUp() throws Exception {
        requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "webCertInfo=j%3A%7B%22userHash%22%3A%2221474836247%22%7D");

    }

    @Test
    public void findAutoCompliteList(){

        AutoComplite[] autoCompliteList = this.restTemplate
                .getForObject("/Content/V1/findAutoCompliteList/autoComplteKind", AutoComplite[].class);


        assertThat(autoCompliteList).isNotNull();

    }


    @Test
    public  void findEventList(){

        String reqUrl = "/Content/V1/findContentList"
            +"/SEARCHTEXT"
            +"/1"
            +"/2";

        EventContent[] content = this.restTemplate
            .getForObject(reqUrl, EventContent[].class);

        List<EventContent> objects = Arrays.asList(content);
        new LogUtil().printObjectList(objects);

        assertThat(content).isNotNull();


    }

    @Test
    @Commit
    public void addContent () {

        List<EventLocation> eventLocations = new ArrayList<>();

        eventLocations.add(EventLocation.builder().latitude(1)
                                        .longitude(2)
                                        .address(RandomUti.randomString(9))
                                        .addressDtls(RandomUti.randomString(9))
                                        .useYn("Y")
                                        .build()
        );

        eventLocations.add(EventLocation.builder().latitude(3)
                                        .longitude(4)
                                        .address(RandomUti.randomString(9))
                                        .addressDtls(RandomUti.randomString(9))
                                        .useYn("Y")
                                        .build()
        );

        EventContent eventContent = EventContent.builder().userHash(RandomUti.randomString(9))
                                                .title(RandomUti.randomString(9))
                                                .eventDesc(RandomUti.randomString(9))
                                                .eventStart(new Date())
                                                .eventEnd(new Date())
                                                .repeatKind(Codes.EV_REPKIND.M1)
                                                .refPath(RandomUti.randomString(3))
                                                .eventLocations(eventLocations)
                                                .tags(RandomUti.randomString(3))
                                                .stat(Codes.EV_STAT.S2)
                                                .build();


        EventContent content = (EventContent) restTemplate.exchange("/Content/V1/AddContent", HttpMethod.POST
                                                    , new HttpEntity(eventContent, requestHeaders)
                                                    , EventContent.class)
                                                .getBody();



        new LogUtil().printObject(content);
        assertThat(content.getEventContentNo()).isGreaterThan(0);
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

        EventContent content = (EventContent) restTemplate.exchange("/Content/V1/AddContent", HttpMethod.POST
                                                                , new HttpEntity(eventContent, requestHeaders)
                                                                , EventContent.class)
                                                          .getBody();



        new LogUtil().printObject(content);
        assertThat(content.getEventContentNo()).isGreaterThan(0);
    }


    @Test
    public void addContent_Not_UserHash () {

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

        EventContent content = (EventContent) restTemplate.exchange("/Content/V1/AddContent", HttpMethod.POST
            , new HttpEntity(eventContent)
            , EventContent.class)
                                                          .getBody();



        new LogUtil().printObject(content);
        assertThat(content.getEventContentNo()).isEqualTo(0);
    }

    @Test
    public void addContentAlarm () {

        ContentAlarm contentAlarm = ContentAlarm.builder().eventContentNo(85).build();


        ContentAlarm resutl = (ContentAlarm) restTemplate.exchange("/Content/V1/AddContentAlarm", HttpMethod.POST
            , new HttpEntity(contentAlarm,requestHeaders)
            , ContentAlarm.class)
             .getBody();

        new LogUtil().printObject(resutl);
        assertThat(resutl.getContentAlarmNo()).isGreaterThan(0);
    }

    @Test
    public void updateContentAlarm () {

        Integer result = restTemplate.exchange("/Content/V1/UpdateContentAlarm/2/N", HttpMethod.PATCH
            , new HttpEntity(null, requestHeaders)
            , Integer.class).getBody();
        new LogUtil().printObject(result);
        assertThat(result).isGreaterThan(0);
    }







}

