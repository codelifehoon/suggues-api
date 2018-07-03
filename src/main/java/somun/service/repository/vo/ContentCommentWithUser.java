package somun.service.repository.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import somun.service.repository.vo.content.ContentComment;
import somun.service.repository.vo.user.User;

/**
 * The persistent class for the user database table.
 * 
 */
@Data
@Builder
@Slf4j
public class ContentCommentWithUser implements Serializable {
	private static final long serialVersionUID = 1L;

	ContentComment contentComment;
	User user;
}

