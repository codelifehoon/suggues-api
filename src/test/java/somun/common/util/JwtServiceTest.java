package somun.common.util;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.service.JwtService;
import somun.service.repository.vo.user.User;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Application.class)
public class JwtServiceTest {



    @Autowired
    JwtService jwtService;



    @Test
    public void jwtTest() throws UnsupportedEncodingException {
        String key = "data";
        String subject ="userInfo";

        User user = User.builder().userNm("user_nm").userId("user_id").build();
        String jwtString = jwtService.createJwt(key,user);
        log.debug(jwtString);

        Object o = jwtService.parseJwt(key, jwtString);

        log.debug("###################" + o.toString()) ;

    }



}
