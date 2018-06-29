package somun.batch;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import somun.common.util.DateUtils;
import somun.service.repositoryClient.visitkoreaTour.commonInfo.Content;
import somun.service.repositoryClient.visitkoreaTour.detailCommon.DetailCommon;
import somun.service.repositoryClient.visitkoreaTour.extraImage.ExtraImage;
import somun.service.repositoryClient.visitkoreaTour.introduceInfo.Introduce;
import somun.service.repositoryClient.visitkoreaTour.repeatInfo.Repeat;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
public class TourAPIClientTest {


    RestTemplate restTemplate;

    String tourServiceKey = "ilxc907Y8gZ92tPsEuhPpIJWpTgCNedxQO5zeg6bdCPqzleiueMFF1xQVn5psxiwUvZTvlmqF/mEQ3O7gbtlew==";;
    String contentDefaultUrl;
    String introduceUrl;
    String repeatInfoUrl;
    String extraImageUrl;
    String detailCommonUrl;


    HttpHeaders requestHeaders;
    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        requestHeaders = new HttpHeaders();
//        requestHeaders.add("Cookie", "webCertInfo=j%3A%7B%22userHash%22%3A%2221474836247%22%7D");

        contentDefaultUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" +
            "?ServiceKey="+ this.tourServiceKey +
            "&contentTypeId=15" +
            "&areaCode=" +
            "&sigunguCode=" +
            "&cat1=" +
            "&cat2=" +
            "&cat3=" +
            "&listYN=Y" +
            "&MobileOS=ETC" +
            "&MobileApp=TourAPI3.0_Guide" +
            "&arrange=A" +
            "&eventStartDate=20180626" +
            "&numOfRows=2000" +
            "&pageNo=1";


        introduceUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro" +
            "?ServiceKey=" + this.tourServiceKey +
            "&contentTypeId=15" +
            "&contentId=2509701" +
            "&MobileOS=ETC" +
            "&MobileApp=GokGokApp" +
            "&introYN=Y";

        repeatInfoUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo" +
            "?ServiceKey=" + this.tourServiceKey +
            "&contentTypeId=15" +
            "&contentId=2509701" +
            "&MobileOS=ETC" +
            "&MobileApp=GokGokApp" +
            "&listYN=Y";

        extraImageUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?ServiceKey=" +this.tourServiceKey +
            "&contentTypeId=15&contentId=2386828&MobileOS=ETC&MobileApp=GokGokApp&imageYN=Y";


        detailCommonUrl ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon" +
            "?ServiceKey="  + this.tourServiceKey +
            "&contentTypeId=15" +
            "&contentId=2386828" +
            "&MobileOS=ETC" +
            "&MobileApp=GokGokApp" +
            "&defaultYN=Y" +
            "&firstImageYN=Y" +
            "&areacodeYN=Y" +
            "&catcodeYN=Y" +
            "&addrinfoYN=Y" +
            "&mapinfoYN=Y" +
            "&overviewYN=Y" +
            "&transGuideYN=Y";


    }

    @Test
    public void tourDefaultContentAPI(){
        String body = restTemplate.exchange(contentDefaultUrl
            , HttpMethod.GET
            , null
            , String.class).getBody();


//        log.debug(body);
        Content response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                        .getAsJsonObject()
                                                        .get("response")
                                                        .toString()
                                            , Content.class);
        log.debug("#####################");
        log.debug(String.valueOf(response.getBody().getItems().getItem().size()));
        assertThat(response.getHeader().getResultCode()).isEqualTo("0000");
    }


    @Test
    public void tourIntroduceAPI(){
        String body = restTemplate.exchange(introduceUrl
            ,HttpMethod.GET
            ,null
            ,String.class).getBody();

        Introduce response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                           .getAsJsonObject()
                                                           .get("response")
                                                           .toString()
            , Introduce.class);
        assertThat(response.getHeader().getResultCode()).isEqualTo("0000");


    }

    @Test
    public void tourRepeatAPI(){
        String body = restTemplate.exchange(repeatInfoUrl
            ,HttpMethod.GET
            ,null
            ,String.class).getBody();


        Repeat response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                           .getAsJsonObject()
                                                           .get("response")
                                                           .toString()
            , Repeat.class);
        assertThat(response.getHeader().getResultCode()).isEqualTo("0000");


    }

    @Test
    public void tourExtraImageAPI(){
//        extraImageUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?ServiceKey=" +this.tourServiceKey +
//            "&contentTypeId=15&contentId=2386828&MobileOS=ETC&MobileApp=GokGokApp&imageYN=Y";


        extraImageUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?ServiceKey=" +this.tourServiceKey +
            "&contentTypeId=15&contentId=2509701&MobileOS=ETC&MobileApp=GokGokApp&imageYN=Y";

        String body = restTemplate.exchange(extraImageUrl
            ,HttpMethod.GET
            ,null
            ,String.class).getBody();

        String response1 = new Gson().fromJson(body, JsonElement.class)
                                     .getAsJsonObject()
                                     .get("response")
                                     .toString();
        log.debug("##########################");
        log.debug(response1);

        ExtraImage response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                        .getAsJsonObject()
                                                        .get("response")
                                                        .toString()
            , ExtraImage.class);
        assertThat(response.getHeader().getResultCode()).isEqualTo("0000");


    }

    @Test
    public void detailCommonAPI(){


        String body = restTemplate.exchange(detailCommonUrl
            ,HttpMethod.GET
            ,null
            ,String.class).getBody();

        String response1 = new Gson().fromJson(body, JsonElement.class)
                                     .getAsJsonObject()
                                     .get("response")
                                     .toString();
        log.debug("##########################");
        log.debug(response1);

        DetailCommon response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                              .getAsJsonObject()
                                                              .get("response")
                                                              .toString()
            , DetailCommon.class);
        assertThat(response.getHeader().getResultCode()).isEqualTo("0000");


    }





    @Test
    public  void dateTest(){
        log.debug(DateUtils.addDayString("yyyyMMdd", 365));


    }

}
