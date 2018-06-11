package somun.service.repository;

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
@Table(name="push_history")
@NamedQuery(name="PushHistory.findAll", query="SELECT u FROM PushHistory u")
public class PushHistory implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="push_history_no")
	@ApiModelProperty(notes = "일련번호" , hidden = true)
	private int pushHistoryNo;

	@Column(name="pushSubscriptionNo")
	@ApiModelProperty(notes = "push 신청일련번호",required = true)
	private Integer pushSubscriptionNo;

	@Column(name="user_no")
	@ApiModelProperty(notes = "사용자 번호",required = true)
	private Integer userNo;

	@Column(name="event_content_no")
	@ApiModelProperty(notes = "발송 컨텐츠 일련번호",required = true)
	private Integer eventContentNo;

	@Column(name="push_payload")
	@ApiModelProperty(notes = "발송 내용",required = true)
	private String pushPayload;


	@Column(name="push_status_code")
	private String pushStatusCode;

	@Lob
	@Column(name="push_result_content")
	private String pushResultContent;

	@ApiModelProperty(notes = "생성자",required = true)
	@Column(name="create_no")
	private Integer createNo;

	@ApiModelProperty(notes = "생성일",required = true)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="create_dt")
	private Date createDt;

}