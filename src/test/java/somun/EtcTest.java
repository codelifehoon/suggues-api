package somun;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;
import somun.config.properties.SomunProperties;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Application.class)
public class EtcTest {

    @Autowired
    SomunProperties somunProperties;
    @Test
    public  void enumNameTest(){
        log.debug("########" + Codes.CONTPROV.seouldata.name());
    }

    @Test
    public  void findStringTest(){
        String html = "에는 공영주차장이 없습니다. 인근 .주차장이용에 참고하세요.<br /><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAl";
        System.out.println(html.contains(";base64,"));
        System.out.println(html.matches(".*;base65,.*"));
        System.out.println(html.matches(".*;base64,.*|.*주차장.*"));
        System.out.println(html.matches(".*dsdsads.*|.*.주차장.*"));
        System.out.println(html.matches(".*;base64,.*|.*dsdsads.*"));
        System.out.println(html.matches(".*dsdsadsds.*|.*dsdsads.*"));


    }

    @Test
    public  void findStringTest2(){
        String html = "https://s.w.org/images/core/emoji/2.2.1/svg/.26ab.svg";


        System.out.println(html.matches("(?s).*emoji.*|(?s).*26ab.*"));
        System.out.println(html.matches("(?s).*sdsdsa.*|(?s).*26ab.*"));
        System.out.println(html.matches("(?s).*emoji.*|(?s).*cdddfdsdf.*"));
        System.out.println(html.matches("(?s).*cdscxzcxz.*|(?s).*cdscsdd.*"));


    }

    @Test
    public  void customPropertiesTest(){

        log.info(somunProperties.getContentProvider().getSeoulData().getServiceKey());
    }

    @Test
    public  void codesTest(){

        log.debug(Codes.EV_STAT.S2.name());
    }


}
