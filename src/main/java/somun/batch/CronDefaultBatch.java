package somun.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CronDefaultBatch {

    @Scheduled(fixedDelay = 1000)
    public void otherJob() {


         }





}
