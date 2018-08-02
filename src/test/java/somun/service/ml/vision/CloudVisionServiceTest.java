package somun.service.ml.vision;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import somun.Application;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Application.class)
public class CloudVisionServiceTest {

    @Autowired
    CloudVisionService cloudVisionService;

//    String filePath = "gs://suggest-life.appspot.com/docUploadImageDev/20180802_175417.jpg";
    String filePath = "gs://suggest-life.appspot.com/docUploadImageDev/259e14b9-c706-423c-9f99-8ab94adaa40e.jpg";
//        String filePath = "https://cdn.clien.net/web/api/file/F01/7296272/17321fab2608db.jpg?w=780&h=30000&gif=false";
    @Test
    public void imageRecognitionText() throws Exception {
        List<String> strings = cloudVisionService.detectText(filePath);
        System.out.println(strings);
    }

    @Test
    public void imageRecognitionWeb() throws Exception {
        List<String> strings = cloudVisionService.detectWebDetections(filePath);
        System.out.println(strings);

    }

}
