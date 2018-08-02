package somun.codesample;

import org.junit.Test;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import somun.service.repository.vo.WebCertInfo;

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


    @Test
    public void hashTest(){
        String hash = "userId1" +" userProvider1";

        System.out.print("#############");
        System.out.print(hash.hashCode());

    }

}

