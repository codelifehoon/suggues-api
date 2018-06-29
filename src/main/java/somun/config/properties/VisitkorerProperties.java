package somun.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "provider.visitkorer")
public class VisitkorerProperties {

    String serviceKey;
    String numOfRows;
}
