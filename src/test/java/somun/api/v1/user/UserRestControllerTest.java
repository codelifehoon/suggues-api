package somun.api.v1.user;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.common.util.LogUtil;
import somun.common.util.RandomUti;
import somun.service.repository.user.UserRepository;
import somun.service.repository.vo.user.User;
import somun.service.repository.vo.user.UserForLogin;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserRestControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void findUserOne(){

        User user = this.restTemplate
                .getForObject("/User/V1/findUserOne/111/111", User.class);

        new LogUtil().printObject(user);

        assertThat(user).isNotNull();

    }

    @Test
    public void AddUser(){

        Date date = new Date();
        User user = User.builder()
                .userId("userId2")
                .userProvider("userProvider2")
                .userNm(RandomUti.randomString(3))
                .userDesc(RandomUti.randomString(10) )
                .userStat(Codes.USER_STAT.S1)
                .createNo(RandomUti.randomNumber(3))
                .createDt(new Date())
                .build();
        UserForLogin userForLogin = restTemplate.postForObject("/User/V1/AddUser", user, UserForLogin.class);
        new LogUtil().printObject(userForLogin);

        assertThat(userForLogin).isNotNull();

    }


    @Test
    @Commit
    public void UpdateUser(){

        Date date = new Date();
        User user = User.builder()
                .userStat(Codes.USER_STAT.S9)
                .build();

        log.debug("############");

        String post = restTemplate.patchForObject("/User/V1/UpdateUser/1", user, String.class);

        log.debug("############");
        log.debug(post);
        assertThat(post).isNotEmpty();

    }



}

