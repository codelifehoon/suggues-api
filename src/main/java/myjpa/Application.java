package myjpa;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* @SpringBootApplication
        @EnableAutoConfiguration, @ComponentScan merge annotation



* */
@SpringBootApplication
//@EnableTransactionManagement
public class Application {

        public  static void  main(String[] args){
            SpringApplication.run(Application.class,args);
        }

}
