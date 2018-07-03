package somun.service.repository.vo;

import lombok.Builder;
import lombok.Data;
import somun.service.repository.vo.user.User;

/**
 * The persistent class for the address database table.
 * 
 */

@Data
@Builder
public class WebCertInfo{
	User user;
}