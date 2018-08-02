package somun.service.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.service.repository.vo.WebCertInfo;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WebCertServiceTest {


    @Autowired
    WebCertService webCertService;

    @Test
    public void webCertInfoBuild()
    {

        String webCertInfoStr ="j:{\"userHash\":\"21474836247\"}";

        assertThat(webCertService.webCertInfoBuild(webCertInfoStr)).isNotNull();

    }

    @Test
    public void webCertInfoJwtBuild()
    {

        String webCertInfoStr ="j:{\"userHash\":\"eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTMyNTk5NTgxMjAwLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiLTEyNDM0NTg1NTBSV1dQWllTR08ifQ.abs16HaPGdiCx5JaGHIDCVo6LNp96QKbcRDG1xYxg6k\"}";
        WebCertInfo webCertInfo = webCertService.webCertInfoBuild(webCertInfoStr);
        log.debug(webCertInfo.toString());

        assertThat(webCertService.webCertInfoBuild(webCertInfoStr)).isNotNull();

    }

}
