package somun.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "somun.properties")
public class SomunProperties {

    private String searchApiServer;
    private String jwtSecret;

    // web push 설정
    private String vapidPublicKey;
    private String vapidPrivateKey;
    private String vapidAdmin;

}
