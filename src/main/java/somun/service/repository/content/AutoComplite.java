package somun.service.repository.content;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The persistent class for the address database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class AutoComplite implements Serializable {



	@ApiModelProperty(notes = "자동완성 문구" )
	private String autoCompliteText;

	@ApiModelProperty(notes = "자동완성 종류")
	private String autoCompliteKind;



}