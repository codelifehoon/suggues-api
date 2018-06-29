package somun.service.repositoryComb;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import somun.service.repository.content.ContentAlarm;
import somun.service.repository.content.ContentThumbUp;
import somun.service.repository.content.EventContent;
import somun.service.repository.content.EventLocation;
import somun.service.repository.user.User;

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
	EventLocation eventLocation;
	Integer commentCnt;
	Boolean isEqualLoginUser;

}

