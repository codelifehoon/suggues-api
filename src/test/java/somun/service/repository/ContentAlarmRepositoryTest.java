package somun.service.repository;

import java.util.ArrayList;
import java.util.Date;
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
        Integer stat = contentAlarmRepository.updateContentAlarmStat(ContentAlarm.builder()
                                                                                 .contentAlarmNo(1)
                                                                                 .updateDt(new Date())
                                                                                 .updateNo(1)
                                                                                 .useYn("N")
                                                                                 .build());
        assertThat(stat).isEqualTo(0);

    }

    @Test
    public void updateContentAlarmStat_Update(){

        Integer stat = contentAlarmRepository.updateContentAlarmStat(ContentAlarm.builder()
                                                                                 .contentAlarmNo(2)
                                                                                 .updateDt(new Date())
                                                                                 .updateNo(30)
                                                                                 .useYn("N")
                                                                                 .build());
        assertThat(stat).isEqualTo(1);

    }

    @Test
    public void findByEventContentNoInAndUseYn(){

        List<Integer> eventContentNos = new  ArrayList<>();
        eventContentNos.add(1);
        eventContentNos.add(85);
        List<ContentAlarm> result = contentAlarmRepository.findByEventContentNoInAndUseYn(eventContentNos, "Y");
        assertThat(result.size()).isEqualTo(2);
    }



}

