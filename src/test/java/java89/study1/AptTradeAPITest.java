package java89.study1;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
public class AptTradeAPITest {

    String URL ="http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?1=1";
    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }


    private String getRestCallResult(String param) {
        return getRestTemplate().exchange(URL + param
            , HttpMethod.GET
            , null
            , String.class).getBody();
    }


    @Test
    public  void getdate(){
        String format = String.format("&ServiceKey=%s&pageNo=1&numOfRows=10&LAWD_CD=%s&DEAL_YMD=%s"
            , "HTwaQzxEWB4Ce/UQ6svGENGTxWdNNtucdMwr/5UA+aNuUY9lncsf2yW0fQhziCIwFBVc0uNLvZHM7MSqanEJ+A=="
            , "11110"
            , "201801"
        );
        log.debug(getRestCallResult(format));

    }
}
