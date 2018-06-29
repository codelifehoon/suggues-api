package somun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* @SpringBootApplication
        @EnableAutoConfiguration, @ComponentScan merge annotation



* */
@SpringBootApplication
//@ServletComponentScan
//@EnableTransactionManagement
public class Application {


    public  static void  main(String[] args){
            SpringApplication.run(Application.class,args);
        }
}
