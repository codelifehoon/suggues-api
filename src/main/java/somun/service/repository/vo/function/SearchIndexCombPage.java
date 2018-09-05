package somun.service.repository.vo.function;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

/**
 * The persistent class for the address database table.
 * 
 */

@Slf4j
public class SearchIndexCombPage extends PageImpl<SearchIndexComb> {

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	// Note: I don't need a sort, so I'm not including one here.
	// It shouldn't be too hard to add it in tho.
	public SearchIndexCombPage(@JsonProperty("content") List<SearchIndexComb> content,
                               @JsonProperty("number") int number,
                               @JsonProperty("size") int size,
                               @JsonProperty("totalElements") Long totalElements,
							   @JsonProperty("pageable") JsonNode pageable,
							   @JsonProperty("last") boolean last,
							   @JsonProperty("totalPages") int totalPages,
							   @JsonProperty("sort") JsonNode sort,
							   @JsonProperty("first") boolean first,
							   @JsonProperty("numberOfElements") int numberOfElements) {
		super(content, new PageRequest(number, size), totalElements);
	}

	public SearchIndexCombPage(List<SearchIndexComb> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public SearchIndexCombPage(List<SearchIndexComb> content) {
		super(content);
	}

	public SearchIndexCombPage() {
		super(new ArrayList());
	}


}