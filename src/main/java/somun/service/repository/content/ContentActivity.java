package somun.service.repository.content;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;

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
@DynamicUpdate
@Table(name="content_activity")
public class ContentActivity implements Serializable {


	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="content_activity_no")
	@ApiModelProperty(notes = "일련번호" , hidden = true)
	private Integer contentActivityNo;

	@ApiModelProperty(notes = "관련 컨텐츠 코드" , required = true)
	@Column(name="activity_code")
	@Enumerated(EnumType.STRING)
	private Codes.ACTIVITY_CODE activityCode;


	@ApiModelProperty(notes = "관련 컨텐츠 일련번호" , required = true)
	@Column(name="activity_ref_no")
	private Integer activityRefNo;

	@ApiModelProperty(notes = "컨텐츠 상태")
	@Column(name="activity_stat")
	@Enumerated(EnumType.STRING)
	private Codes.ACTIVITY_STAT activityStat;

	@ApiModelProperty(notes = "셍성자")
	@Column(name="create_no")
	private Integer createNo;

	@ApiModelProperty(notes = "등록일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_dt")
	private Date createDt;

	@ApiModelProperty(notes = "수정자")
	@Column(name="update_no")
	private Integer updateNo;

	@ApiModelProperty(notes = "수정일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_dt")
	private Date updateDt;

}