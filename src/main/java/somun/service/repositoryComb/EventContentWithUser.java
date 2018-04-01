package somun.service.repositoryComb;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import somun.service.repository.ContentAlarm;
import somun.service.repository.ContentThumbUp;
import somun.service.repository.EventContent;
import somun.service.repository.User;

/**
 * The persistent class for the user database table.
 * 
 */
@Data
@Builder
@Slf4j
public class EventContentWithUser implements Serializable {
	private static final long serialVersionUID = 1L;

	EventContent eventContent;
	User user;
	ContentThumbUp contentThumbUp;
	ContentAlarm contentAlarm;
	Integer commentCnt;

}

