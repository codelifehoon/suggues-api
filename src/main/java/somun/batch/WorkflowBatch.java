package somun.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import somun.common.util.DateUtils;
import somun.config.properties.SomunProperties;

@Slf4j
@Service
public class WorkflowBatch {

    @Autowired
    SeoulDataBatch seoulDataBatch;

    @Autowired
    VisitKoreaBatch visitKoreaBatch;

    @Autowired
    private SomunProperties somunProperties;

    @Scheduled(cron = "0 0 2 * * *")
    public int updateAllProvider() {

        int visitKoreaContent = visitKoreaBatch.getVisitKoreaContent();
        long regVisitKoreaContentToEventContent = visitKoreaBatch.regVisitKoreaContentToEventContent();
        int listPublicReservationCulture = seoulDataBatch.getSeoulDataContent("ListPublicReservationCulture");
        int listPublicReservationEducation = seoulDataBatch.getSeoulDataContent("ListPublicReservationEducation");
        long regSeoulDataContentToEventContent = seoulDataBatch.regSeoulDataContentToEventContent();
        Integer mergeTotalSearchIndex = mergeTotalSearchIndex();

        log.error(
                    String.format("visitKoreaContent: %d,regVisitKoreaContentToEventContent: %d,listPublicReservationCulture: %d,listPublicReservationEducation: %d,regSeoulDataContentToEventContent: %d,mergeTotalSearchIndex: %d",visitKoreaContent
                        ,regVisitKoreaContentToEventContent
                        ,listPublicReservationCulture
                        ,listPublicReservationEducation
                        ,regSeoulDataContentToEventContent
                        ,mergeTotalSearchIndex)
                    );

        return mergeTotalSearchIndex;
    }



    private Integer mergeTotalSearchIndex() {
        String url = somunProperties.getSearchApiServer() + String.format("/Engine/V1/INDEX/mergeTotalSearchIndex/%s/%s"
            , DateUtils.addDayString("yyyy-MM-dd", -2)
            , DateUtils.addDayString("yyyy-MM-dd",2));
        return new RestTemplate().exchange(url, HttpMethod.POST
            , new HttpEntity(null, new HttpHeaders())
            , Integer.class).getBody();
    }

}
