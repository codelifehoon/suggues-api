package somun.service.repository.function;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name="push_subscription")
@NamedQuery(name="PushSubscription.findAll", query="SELECT u FROM PushSubscription u")
public class PushSubscription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pushSubscriptionNo")
	@ApiModelProperty(notes = "일련번호" , hidden = true)
	private int pushSubscriptionNo;

	@Column(name="user_no")
	@ApiModelProperty(notes = "사용자 번호", hidden = true , required = true)
	private Integer userNo;


	@ApiModelProperty(notes = "push endpoint", required = true)
	@Lob
	@Column(name="endpoint")
	private String endPoint;

	@ApiModelProperty(notes = "push endpoint Hash", hidden = true )
	@Column(name="endpointHash")
	private String endPointHash;

	@Column(name="use_yn")
	@ApiModelProperty(notes = "사용 여부", hidden = true, required = true)
	private String useYn;

	@ApiModelProperty(notes = "생성자", hidden = true , required = true)
	@Column(name="create_no")
	private Integer createNo;

	@ApiModelProperty(notes = "생성일", hidden = true , required = true)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="create_dt")
	private Date createDt;

	@ApiModelProperty(notes = "수정자", hidden = true )
	@Column(name="update_no")
	private Integer updateNo;

	@ApiModelProperty(notes = "수정일", hidden = true )
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="update_dt")
	private Date updateDt;



}