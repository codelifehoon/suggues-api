package somun.service.repository.vo.content;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;

/**
 * The persistent class for the user database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name="content_comment")
@NamedQuery(name="ContentComment.findAll", query="SELECT u FROM ContentComment u")
public class ContentComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="content_comment_no")
	@ApiModelProperty(notes = "자동생성일련번호" , hidden = true)
	private Integer contentCommentNo;

	@Column(name="event_content_no")
	@ApiModelProperty(notes = "관련 컨텐츠 번호" , required = true)
	private Integer eventContentNo;

	@Column(name="user_no")
	@ApiModelProperty(notes = "사용자 번호", hidden = true, required = true)
	private Integer userNo;

	@Column(name="comment_desc")
	@Size(max=8000)
	@ApiModelProperty(notes = "게시물 의견", required = true)
	private String commentDesc;

	@Column(name="comment_pw")
	@ApiModelProperty(notes = "로그인 안했을 경우 비번등록")
	private String commentPw;

	@Column(name="stat")
	@ApiModelProperty(notes = "등록상태", required = true)
	@Enumerated(EnumType.STRING)
	private Codes.EV_STAT stat;

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

