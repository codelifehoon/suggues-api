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
public class WorkflowBatchTest {
    @Autowired
    WorkflowBatch workflowBatch;

    @Test
    public void updateVisitKoreaSearchDocBatch(){
        workflowBatch.updateVisitKoreaSearchDoc();
    }
}
