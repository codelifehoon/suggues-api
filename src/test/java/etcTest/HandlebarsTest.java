package etcTest;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Template;

import lombok.extern.slf4j.Slf4j;
import somun.Application;
import somun.common.service.HandlebarsTemplateLoader;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HandlebarsTest {

    @Autowired
    HandlebarsTemplateLoader  handlebarsTemplateLoader;

    @Test
    public void handlebarMapping() throws IOException {

        String responseJson = "{\"title\":\"Mr.\",\"name\":\"ABC\",\"address\":{\"address1\":\"address1\",\"address2\":\"address2\"," +
            "\"city\":\"XYZ\",\"state\":\"State1\"}}";

        JsonNode jsonNode = new ObjectMapper().readValue(responseJson, JsonNode.class);
        Template template = handlebarsTemplateLoader.getTemplate("home");

        String apply = template.apply(handlebarsTemplateLoader.getContext(jsonNode));
        System.out.println(apply);
    }


    @Test
    public void handlebarMapping_with_map() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("title","handlebarMapping_with_map");


        Template template = handlebarsTemplateLoader.getTemplate("home");

        String apply = template.apply(handlebarsTemplateLoader.getContext(map));
        System.out.println(apply);
    }

    @Test
    public void sdata_with_map() throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("SVCNM","서비스명");
        map.put("TELNO","010-000-0000");
        map.put("SVCURL","http://www.11st.co.kr");
        map.put("NOTICE","공지사항");
        map.put("DTLCONT","상세<b>정보</b>상세정보");
        map.put("IMAGES","http://i.011st.com/ex_t/R/400x400/1/85/0/src/pd/16/4/5/6/7/6/5/HTnrk/1615456765_B.jpg");


        Template template = handlebarsTemplateLoader.getTemplate("seouldata");

        String apply = template.apply(handlebarsTemplateLoader.getContext(map));
        System.out.println(apply);
    }

    @Test
    public void vk_with_map() throws IOException {
        HashMap<String, Object> map = new HashMap<>();

        map.put("OVERVIEW","오버뷰");
        map.put("ADDR1","주소1");
        map.put("ADDR2","주소2");
        map.put("TEL","연락처");
        map.put("HOMEPAGE","www.11st.co.kr");
        map.put("FIRSTIMAGE1","img1");
        map.put("FIRSTIMAGE2","Barbatus frondator inciviliter dignuss detrius est.");
        map.put("EXTRAIMAGEITEMLIST",new String[]{"Nix ires, tanquam talis vortex.","Caesium de raptus pes, resuscitabo finis!"});


        Template template = handlebarsTemplateLoader.getTemplate("visitkorea");

        String apply = template.apply(handlebarsTemplateLoader.getContext(map));
        System.out.println(apply);
    }






}
