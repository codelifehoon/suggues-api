package somun.service.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.Application;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class ContentAlarmRepositoryTest {

    @Autowired
    ContentAlarmRepository contentAlarmRepository;

    @Test
    public void updateContentAlarmStat_notUpdate(){
        Integer stat = contentAlarmRepository.updateContentAlarmStat(1, 1, "Y");
        assertThat(stat).isEqualTo(0);

    }

    @Test
    public void updateContentAlarmStat_Update(){
        Integer stat = contentAlarmRepository.updateContentAlarmStat(2, 30, "Y");
        assertThat(stat).isEqualTo(1);

    }

    @Test
    public void findByEventContentNoInAndUseYn(){

        List<Integer> eventContentNo = new  ArrayList<>();
        eventContentNo.add(1);
        eventContentNo.add(85);
        List<ContentAlarm> result = contentAlarmRepository.findByEventContentNoInAndUseYn(eventContentNo, "Y");
        assertThat(result.size()).isEqualTo(2);
    }



}

