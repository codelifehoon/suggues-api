package somun.service.repository;

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
public class PushSubscriptionRepositoryTest {

    @Autowired
    PushSubscriptionRepository pushSubscriptionRepository;

    @Test
    public void findAll() {
        Iterable<PushSubscription> all = pushSubscriptionRepository.findAll();
        assertThat(all).isNotNull();
    }
}
