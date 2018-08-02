package somun.service.repository.vo.user;

import lombok.Builder;
import lombok.Data;
import somun.common.biz.Codes;


@Data
@Builder
public class UserForLogin {

    String userHash;
    Codes.USER_STAT userStat;

}
