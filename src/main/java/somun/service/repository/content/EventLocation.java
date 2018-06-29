package somun.service.repository.content;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
@Entity
@Table(name="event_location")
@NamedQuery(name="EventLocation.findAll", query="SELECT u FROM EventLocation u")
public class EventLocation implements Serializable {


	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="event_location_no")
	@ApiModelProperty(notes = "일련번호" ,hidden = true)
	private Integer eventLocationNo;

	@ApiModelProperty(notes = "관련 content 번호"  ,hidden = true)
	@Column(name="event_content_no")
	private Integer eventContentNo;

	@ApiModelProperty(notes = "latitude 좌표" , required = true)
	@Column(name="latitude")
	private double latitude;


	@ApiModelProperty(notes = "longitude 좌표" , required = true)
	@Column(name="longitude")
	private double longitude;

	@ApiModelProperty(notes = "주소")
	@Column(name="address")
	private String address;

	@ApiModelProperty(notes = "주소상세")
	@Column(name="addressDtls")
	private String addressDtls;

	@ApiModelProperty(notes = "사용여부" ,required = true)
	@Column(name="use_yn" )
	private String useYn;

	@ApiModelProperty(notes = "생성자",hidden = true, required = true)
	@Column(name="create_no")
	private Integer createNo;

	@ApiModelProperty(notes = "생성일",hidden = true, required = true)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="create_dt")
	private Date createDt;

	@ApiModelProperty(notes = "수정자",hidden = true, required = true)
	@Column(name="update_no")
	private Integer updateNo;

	@ApiModelProperty(notes = "수정일",hidden = true, required = true)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="update_dt")
	private Date updateDt;



}