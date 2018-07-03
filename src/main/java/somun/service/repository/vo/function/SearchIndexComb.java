package somun.service.repository.vo.function;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The persistent class for the address database table.
 * 
 */
@Data
@Builder
@Slf4j
public class SearchIndexComb implements Serializable {
	private Integer eventContentNo;
	private String title;
	private String eventDescText;
	private Date eventStart;
	private Date eventEnd;
	private String tags;
	private Double longitude;
	private Double latitude;
	private Long locationDistance;
}