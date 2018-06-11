package somun.service.repositoryComb;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
public class Payload {
    String title;
    String message;
    String link;

}
