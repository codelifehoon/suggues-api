package somun.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import somun.Application;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ContentProviderBatchTest {

    @Autowired
    SeoulDataBatch seoulDataBatch;

    @Autowired
    VisitKoreaBatch visitKoreaBatch;


    @Test
    public void regVisitKoreaContentToEventContent(){

        visitKoreaBatch.regVisitKoreaContentToEventContent();
    }


    @Test
    public void getVisitKoreaContent(){
        visitKoreaBatch.getVisitKoreaContent();
    }

    @Test
    public void getSeoulDataContent_ListPublicReservationCulture(){
        seoulDataBatch.getSeoulDataContent("ListPublicReservationCulture");
    }
    @Test
    public void getSeoulDataContent_ListPublicReservationEducation(){
        seoulDataBatch.getSeoulDataContent("ListPublicReservationEducation");
    }


    @Test
    public void regSeoulDataContentToEventContent(){

        seoulDataBatch.regSeoulDataContentToEventContent();
    }




}

