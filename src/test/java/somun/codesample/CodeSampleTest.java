package somun.codesample;

import org.junit.Test;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import somun.service.repositoryComb.WebCertInfo;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CodeSampleTest {

    @Test
    public void cookieConverter()
    {

        String webCertInfoStr ="j:{\"userHash\":\"21474836247\"}";

        webCertInfoStr = webCertInfoStr.replace("j:","");
        WebCertInfo w =  new Gson().fromJson(webCertInfoStr, WebCertInfo.class);

        assertThat(w).isNotNull();
    }
}

