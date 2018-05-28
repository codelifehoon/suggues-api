package somun.codesample;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HtmlCaptureSample {
    @Test
    public void htmlCapture()
    {
            //드라이버 초기화
//            driver = new FirefoxDriver();

//            System.setProperty("phantomjs.binary.path","/usr/local/Cellar/phantomjs/2.1.1/bin/phantomjs");


            long iStart = System.currentTimeMillis(); // start timing
            PhantomJSDriver driver = null;

            String userAgent = "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.41 Safari/535.1";
            System.setProperty("phantomjs.page.settings.userAgent", userAgent);

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setJavascriptEnabled(true);
            caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "/usr/local/Cellar/phantomjs/2.1.1/bin/phantomjs"
            );

            caps.setCapability("takesScreenshot", true);



        try {
            driver = new PhantomJSDriver(caps);
            for (int i =0;i<100 ; i++) {
                String baseUrl = "http://www.nate.com";
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                driver.get(baseUrl + "/");

                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                FileUtils.copyFile(scrFile, new File("/Users/codelife/google_screen_sample"+ String.valueOf(i) + ".jpeg"), true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            driver.close();
            driver.quit();
            System.out.println("Single Page Time:" + (System.currentTimeMillis() - iStart));
        }
        }
}

