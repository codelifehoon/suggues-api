package somun.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import somun.api.v1.content.VisitKoreaController;
import somun.common.util.DateUtils;
import somun.config.properties.SomunProperties;

@Slf4j
@Service
public class WorkflowBatch {

    @Autowired
    private VisitKoreaController visitKoreaController;

    @Autowired
    private SomunProperties somunProperties;

//    @Scheduled(cron = "0 0 2 * * *")
    public void updateVisitKoreaSearchDoc() {

        log.error("##### updateVisitKoreaSearchDoc start");

        log.error("##### getVisitKoreaContent start");
        visitKoreaController.getVisitKoreaContent();

        log.error("##### regVisitKoreaContentToEventContent start");
        visitKoreaController.regVisitKoreaContentToEventContent();

        log.error("#####  mergeTotalSearchIndex start");
        String url = somunProperties.getSearchApiServer() + String.format("/Engine/V1/INDEX/mergeTotalSearchIndex/%s/%s"
            ,DateUtils.addDayString("yyyy-MM-dd",-2)
            ,DateUtils.addDayString("yyyy-MM-dd",2));

        Integer count = new RestTemplate().exchange(url, HttpMethod.POST
            , new HttpEntity(null, new HttpHeaders())
            , Integer.class).getBody();

        log.error("#####  mergeTotalSearchIndex count :" + count.toString());
        log.error("##### updateVisitKoreaSearchDoc end");


         }

}
