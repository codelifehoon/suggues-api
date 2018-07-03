package somun.service.repository.vo;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import somun.service.repository.vo.content.ContentActivity;
import somun.service.repository.vo.content.EventContent;

/**
 * The persistent class for the user database table.
 * 
 */
@Data
@Builder
@Slf4j
public class ContentActivityComb implements Serializable {
	private static final long serialVersionUID = 1L;

	EventContent eventContent;
	ContentActivity contentActivity;


}

