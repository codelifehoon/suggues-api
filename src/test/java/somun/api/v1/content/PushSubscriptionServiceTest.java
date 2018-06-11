package somun.api.v1.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.service.repository.PushSubscription;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class PushSubscriptionServiceTest {



    @Autowired
    private TestRestTemplate testRestTemplate;
    private String endPoint;

    HttpHeaders requestHeaders;

    @Before
    public void setUp() throws Exception {
        requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "webCertInfo=j%3A%7B%22userHash%22%3A%2221474836247%22%7D");

        endPoint = "{\"endpoint\":\"https://fcm.googleapis" +
            ".com/fcm/send/c1LeCqonfng:APA91bE25zo6Isg-StYKxxBadNEqxNFkcxYto8k17J_s8zL3USJjHrMFHu50XFrR_gKfi" +
            "-PIw5HTo5rd3AnOMuZpYNiKDryTpyREwWFkdWQJ_pbDmRHBAL-WIERsLI4LKXDuiRdtWCJO\",\"expirationTime\":null," +
            "\"keys\":{\"p256dh\":\"BEkVq-tUCzlU-9QjzyBWYkwiiBXHlLT9gni7a2kOAdger83HbX3tpNq-jC3aP5jeO0W1-nv0icmILUHAhPKAvf8\"," +
            "\"auth\":\"F9wA_qHmv0YpODLODOzp7g\"}}";

    }

    @Test
    @Commit
    public void addPushSubscription () {
        PushSubscription pushSubscription = PushSubscription.builder()
                                                            .endPoint(endPoint)
                                                            .build();

        Integer returnNumber = testRestTemplate.exchange("/Subscription/V1/modifyPushSubscription", HttpMethod.POST
            , new HttpEntity(pushSubscription, requestHeaders)
            , Integer.class).getBody();

        assertThat(returnNumber).isGreaterThan(0);
    }

    @Test
    @Commit
    public void updatePushSubscription () {

        String endPoint = this.endPoint + "****";
        PushSubscription pushSubscription = PushSubscription.builder()
                                                            .endPoint(endPoint)
                                                            .build();

        Integer returnNumber = testRestTemplate.exchange("/Subscription/V1/modifyPushSubscription", HttpMethod.POST
            , new HttpEntity(pushSubscription, requestHeaders)
            , Integer.class).getBody();

        assertThat(returnNumber).isGreaterThan(0);
    }

}
