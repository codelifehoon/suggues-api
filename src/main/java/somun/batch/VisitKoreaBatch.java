package somun.batch;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.util.DateUtils;
import somun.config.properties.SomunProperties;
import somun.service.repository.provider.ContentProviderRepository;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.provider.ContentProvider;
import somun.service.repositoryClient.ContentProviderVisitKoreaService;
import somun.service.repositoryClient.visitkoreaTour.ProviderContetComb;
import somun.service.repositoryClient.visitkoreaTour.VisitKoreaContetComb;
import somun.service.repositoryClient.visitkoreaTour.commonInfo.Content;
import somun.service.repositoryClient.visitkoreaTour.commonInfo.Item;
import somun.service.repositoryClient.visitkoreaTour.detailCommon.DetailCommon;
import somun.service.repositoryClient.visitkoreaTour.extraImage.ExtraImage;
import somun.service.repositoryClient.visitkoreaTour.introduceInfo.Introduce;
import somun.service.repositoryClient.visitkoreaTour.repeatInfo.Repeat;

@Slf4j
@Service
public class VisitKoreaBatch {

    @Autowired
    SomunProperties somunProperties;

    @Autowired
    ContentProviderRepository contentProviderRepository;

    @Autowired
    ContentProviderVisitKoreaService contentProviderVisitKoreaService;



    public long regVisitKoreaContentToEventContent(){
        log.error("#### regVisitKoreaContentToEventContent cron start!");

        Codes.CONTPROV_STAT[] stats = new Codes.CONTPROV_STAT[] {Codes.CONTPROV_STAT.S0 , Codes.CONTPROV_STAT.S1};
        List<ContentProvider> contentProviders = contentProviderRepository.findByStatInAndProvider(stats, Codes.CONTPROV.visitkorea);
        long count = contentProviders.stream().map(d -> {
            try {
                EventContent eventContent = contentProviderVisitKoreaService.mergeIntoProviderContetToEventContent(d);
                if (eventContent == null) {
                    // not formal data is regattering process
                    d.setProviderModifiedtime("-");
                }
                d.setStat(Codes.CONTPROV_STAT.S2);
            } catch (Exception e) {
                d.setStat(Codes.CONTPROV_STAT.S4);
                e.printStackTrace();
                log.error(d.toString());
            }

            d.setUpdateNo(Codes.CONTPROV.visitkorea.getProvNumber());
            d.setUpdateDt(new Date());
            contentProviderRepository.save(d);

            return 1;
        }).count();

        log.error("#### regVisitKoreaContentToEventContent cron end!");

        return count;
    }

    

    public int getVisitKoreaContent(){
        log.error("#### getVisitKoreaContent cron start!");

        String body = getRestCallResult(new Content(),null);



        Content content = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                         .getAsJsonObject()
                                                         .get("response")
                                                         .toString()
            , Content.class);





        List<ProviderContetComb> contetCombs = content.getBody().getItems().getItem().parallelStream()
                                                      .map(d -> {
                                                                 VisitKoreaContetComb build = VisitKoreaContetComb.builder()
                                                                                                                  .item(d)
                                                                                                                  .introduce(getIntrodece
                                                                                                                                 (new Introduce(),
                                                                                                                                          d))
                                                                                                                  .extraImage
                                                                                                                      (getExtraImage(new ExtraImage(),
                                                                                                                                            d))
                //                                                                                                .repeat(getRepeat(new Repeat(), d))
                                                                                                                  .detailCommon(getDetailCommon(
                                                                                                                      new DetailCommon(),
                                                                                                                      d))
                                                                                                                  .build();
                                                                 build.setContProv(Codes.CONTPROV.visitkorea);
                                                                 return build;

                                                             }
                                                        ).collect(Collectors.toList());
        int count = contentProviderVisitKoreaService.mergeIntoContentProvider(contetCombs);
        log.error("#### getVisitKoreaContent cron end!");

        return count;

    }



    private Repeat getRepeat(Object obj,Item item) {
        Repeat response = null;

        try {
        String body = getRestCallResult(obj, item);
        response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                        .getAsJsonObject()
                                                        .get("response")
                                                        .toString()
            , Repeat.class);
        }catch(Exception e) {e.printStackTrace();}
//        assertThat(response.getHeader().getResultCode()).isEqualTo("0000");

        return response;
    }


    private ExtraImage getExtraImage(Object obj,Item item) {
        ExtraImage response = null;

        try {
            String body = getRestCallResult(obj, item);
            response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                     .getAsJsonObject()
                                                     .get("response")
                                                     .toString()
                , ExtraImage.class);
//            assertThat(response.getHeader().getResultCode()).isEqualTo("0000");
        }catch(Exception e) {e.printStackTrace();}

        return response;

    }

    private Introduce getIntrodece(Object obj,Item item) {
        Introduce response= null;
        try {
        String body = getRestCallResult(obj, item);
        response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                            .getAsJsonObject()
                                                            .get("response")
                                                            .toString()
            , Introduce.class);
//        assertThat(response.getHeader().getResultCode()).isEqualTo("0000");
        }catch(Exception e) {e.printStackTrace();}

        return response;
    }

    private DetailCommon getDetailCommon(Object obj,Item item) {
        DetailCommon response= null;
        try {
            String body = getRestCallResult(obj, item);
            response = new Gson().fromJson(new Gson().fromJson(body, JsonElement.class)
                                                     .getAsJsonObject()
                                                     .get("response")
                                                     .toString()
                , DetailCommon.class);
        }catch(Exception e) {e.printStackTrace();}

        return response;
    }


    private String getVisitkoreaURL(Object obj,Item item)
    {
        String tourServiceKey = somunProperties.getContentProvider().getVisitkorer().getServiceKey();
        String URL = "";

        if (obj instanceof Content){

            URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" +
                "?ServiceKey="+ tourServiceKey +
                "&contentTypeId=15" +
                "&areaCode=" +
                "&sigunguCode=" +
                "&cat1=" +
                "&cat2=" +
                "&cat3=" +
                "&listYN=Y" +
                "&MobileOS=ETC" +
                "&MobileApp=GokGokApp" +
                "&arrange=A" +
                "&eventStartDate=" + DateUtils.addDayString("yyyyMMdd ", 0) +
//                "&eventEndDate=" + DateUtils.addDayString("yyyyMMdd ", 365) +
                "&numOfRows=" + somunProperties.getContentProvider().getVisitkorer().getNumOfRows() +
                "&pageNo=1";

        }

        if (obj instanceof Repeat){

            URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo" +
                "?ServiceKey=" + tourServiceKey +
                "&contentTypeId=15" +
                "&contentId=2509701" +
                "&MobileOS=ETC" +
                "&MobileApp=GokGokApp" +
                "&listYN=Y";

        }

        if (obj instanceof Introduce){

            URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro" +
                "?ServiceKey=" + tourServiceKey +
                "&contentTypeId=" + item.getContenttypeid() +
                "&contentId=" + item.getContentid() +
                "&MobileOS=ETC" +
                "&MobileApp=GokGokApp" +
                "&introYN=Y";

        }
        if (obj instanceof ExtraImage){

            URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage" +
                "?ServiceKey=" + tourServiceKey +
                "&contentTypeId=" + item.getContenttypeid() +
                "&contentId=" + item.getContentid() +
                "&MobileOS=ETC" +
                "&MobileApp=GokGokApp" +
                "&imageYN=Y";

        }

        if (obj instanceof DetailCommon){

            URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage" +
                "?ServiceKey=" + tourServiceKey +
                "&contentTypeId=" + item.getContenttypeid() +
                "&contentId=" + item.getContentid() +
                "&MobileOS=ETC" +
                "&MobileApp=GokGokApp" +
                "&imageYN=Y";

            URL ="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon" +
                "?ServiceKey="  + tourServiceKey +
                "&contentTypeId=" + item.getContenttypeid() +
                "&contentId=" + item.getContentid() +
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


        return URL;
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }


    private String getRestCallResult(Object obj, Item item) {
        return getRestTemplate().exchange(getVisitkoreaURL(obj,item)
            , HttpMethod.GET
            , null
            , String.class).getBody();
    }

}

