package somun.service.repository.vo.function;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.extern.slf4j.Slf4j;

/**
 * The persistent class for the address database table.
 * 
 */

@Slf4j
public class SearchIndexCombPage extends PageImpl<SearchIndexComb> {

	@JsonCreator
	// Note: I don't need a sort, so I'm not including one here.
	// It shouldn't be too hard to add it in tho.
	public SearchIndexCombPage(@JsonProperty("content") List<SearchIndexComb> content,
                               @JsonProperty("number") int number,
                               @JsonProperty("size") int size,
                               @JsonProperty("totalElements") Long totalElements) {
		super(content, new PageRequest(number, size), totalElements);
	}

}