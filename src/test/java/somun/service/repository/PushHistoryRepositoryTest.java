package somun.service.repository;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import somun.Application;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class PushHistoryRepositoryTest {

    @Autowired
    PushHistoryRepository pushHistoryRepository;

    private String payLoad;

    @Before
    public void setUp() throws Exception {

        this.payLoad = "";
    }

    private String getPayload() {


        JsonObject jsonObjectSub = new JsonObject();
        jsonObjectSub.addProperty("message","안녕하세요. 이것은 푸시 메세지 입니다  줄바꿈 되나요?");
        jsonObjectSub.addProperty("link","https://google.com");


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", " HI!");
        jsonObject.addProperty("body", jsonObjectSub.toString());
        jsonObject.addProperty("icon", "/images/icons/icon-144x144.png");
        jsonObject.addProperty("badge", "/images/icons/icon-144x144.png");


        return jsonObject.toString();
    }


    @Test
    @Commit
    public  void  savePushHistory(){

        PushHistory pushHistory = PushHistory.builder()
                                             .eventContentNo(1)
                                             .userNo(30)
                                             .createNo(30)
                                             .createDt(new Date())
                                             .pushPayload(this.getPayload())
                                             .pushSubscriptionNo(6)
                                             .build();
        PushHistory history = pushHistoryRepository.save(pushHistory);

        assertThat(history).isNotNull();
    }
}
