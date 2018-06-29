package somun.service.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.service.repository.content.ContentThumbUpRepository;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class ContentThumbUpTest {

    @Autowired
    ContentThumbUpRepository contentThumbUpRepository;

    @Test
    public  void test(){

    }
//
//    @Test
//    public void updateContentThumbUp_notUpdate(){
//        Integer stat = contentThumbUpRepository.updateContentThumbUp(1, 1, "Y");
//        assertThat(stat).isEqualTo(0);
//    }
//
//    @Test
//    public void updateContentThumbUp_Update(){
//        Integer stat = contentThumbUpRepository.updateContentAlarmStat(2, 30, "Y");
//        assertThat(stat).isEqualTo(1);
//
//    }

}

