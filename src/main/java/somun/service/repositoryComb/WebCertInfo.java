package somun.service.repositoryComb;

import lombok.Builder;
import lombok.Data;
import somun.service.repository.User;

/**
 * The persistent class for the address database table.
 * 
 */

@Data
@Builder
public class WebCertInfo{
	User user;
}