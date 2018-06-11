package somun.service;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import somun.Application;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Application.class)
public class SendAlarmServiceTest {

    @Autowired
    SendAlarmService sendAlarmService;


    @Test
    public void sendAlarmMassage() throws ParseException {

        sendAlarmService.sendAlarmMassage();

    }
}

