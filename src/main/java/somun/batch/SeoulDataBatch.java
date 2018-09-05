package somun.batch;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.service.HandlebarsTemplateLoader;
import somun.config.properties.SomunProperties;
import somun.service.repository.provider.ContentProviderRepository;
import somun.service.repository.vo.content.EventContent;
import somun.service.repository.vo.provider.ContentProvider;
import somun.service.repositoryClient.ContentProviderSeoulDataService;
import somun.service.repositoryClient.seoulData.Response;
import somun.service.repositoryClient.visitkoreaTour.ProviderContetComb;
import somun.service.repositoryClient.visitkoreaTour.SeoulDataContetComb;

@Slf4j
@Service
public class SeoulDataBatch {

    @Autowired
    SomunProperties somunProperties;

    @Autowired
    ContentProviderRepository contentProviderRepository;

    @Autowired
    ContentProviderSeoulDataService contentProviderSeoulDataService;

    @Autowired
    HandlebarsTemplateLoader handlebarsTemplateLoader;

    public long regSeoulDataContentToEventContent(){
        log.error("#### regSeoulDataContentToEventContent cron start!");

        Codes.CONTPROV_STAT[] stats = new Codes.CONTPROV_STAT[] {Codes.CONTPROV_STAT.S0 , Codes.CONTPROV_STAT.S1};
        List<ContentProvider> contentProviders = contentProviderRepository.findByStatInAndProvider(stats,Codes.CONTPROV.seouldata);
        long count = contentProviders.stream()
                                     .map(d -> {
                                         try {
                                            EventContent eventContent = contentProviderSeoulDataService.mergeIntoProviderContetToEventContent(d);
                                            if (eventContent == null) { // not formal data is regattering process
                                                d.setProviderModifiedtime("-");
                                            }

                                             d.setStat(Codes.CONTPROV_STAT.S2);


                                            } catch (Exception e) {
                                                 d.setStat(Codes.CONTPROV_STAT.S4);
                                                e.printStackTrace();
                                                log.error("#####" + d.toString());
                                            }

                                         d.setUpdateNo(Codes.CONTPROV.seouldata.getProvNumber());
                                         d.setUpdateDt(new Date());
                                         contentProviderRepository.save(d);

                                         return 1;
        }).count();

        log.error("#### regSeoulDataContentToEventContent cron end!");

        return count;
    }


    public int getSeoulDataContent( String serviceName){
        log.error("#### getSeoulDataContent cron start!");
        int numOfRows = Integer.valueOf(somunProperties.getContentProvider().getSeoulData().getNumOfRows());

        IntStream limit = IntStream.iterate(0, i -> i + 1).limit(numOfRows/1000);

        return limit.map(i -> {

          String body = getRestCallResult(1 + i*1000, (i+1)*1000, serviceName);
          return mergeIntoContentProvider(body);

        }).sum();

    }

    private int mergeIntoContentProvider(String body) {
        try {
            Response seoulDataResponse = new Gson().fromJson(body, Response.class);
            List<ProviderContetComb> contetCombs = seoulDataResponse.getRow()
                                                                    .stream()
                                                                    .filter(d->!StringUtils.isEmpty(d.getDTLCONT())
//                                                                        && !d.getDTLCONT().contains(";base64,")
//                                                                        && !d.getDTLCONT().contains(".svg")
                                                                        && !d.getDTLCONT().matches("(?s).*;base64,.*|(?s).*svg.*")
                                                                    )
//
                                                                    .map(d -> {
                                                                        /*boolean a = !d.getDTLCONT().matches(".*;base64,.*")
                                                                            && !d.getDTLCONT().contains(".svg");
                                                                        boolean b = !d.getDTLCONT().matches(".*;base64,.*|.*svg.*");

                                                                        if (a != b) log.info("##########d.getSVCID():" + d.getSVCID());
*/
                                                                        SeoulDataContetComb build = SeoulDataContetComb.builder()
                                                                                                                       .rowItem(d)
                                                                                                                       .build();
                                                                        build.setContProv(Codes.CONTPROV.seouldata);
                                                                        return build;
                                                                    }).collect(Collectors.toList());
            int count = contentProviderSeoulDataService.mergeIntoContentProvider(contetCombs);
            log.error("#### getSeoulDataContent cron end!");
            return count;
        } catch (RuntimeException e)
        {
            e.printStackTrace();

        }

        return  0;
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }


    private String getRestCallResult(int pageNum, int rowNum, String serviceName) {

        String URL = String.format("http://openapi.seoul.go.kr:8088/%s/json/%s/%d/%d"
           , somunProperties.getContentProvider().getSeoulData().getServiceKey()
           , serviceName
           , pageNum
            , rowNum);


        log.debug(URL);
        String body = getRestTemplate().exchange(URL
            , HttpMethod.GET
            , null
            , String.class).getBody();

        return  new Gson().fromJson(body, JsonElement.class)
                          .getAsJsonObject()
                          .get(serviceName)
                          .toString();
    }

}

