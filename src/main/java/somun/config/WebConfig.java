package somun.config;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*WebConfig 추가하니 swagger 문서 사이트 동작하지 않아 비활성화*/
//@Configuration
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).
            favorParameter(true).
                      defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("xml", MediaType.APPLICATION_XML);
    }
}

