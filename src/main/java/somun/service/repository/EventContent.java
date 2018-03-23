package somun.service.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name="event_content")
//@NamedQuery(name="EventContent.findAll", query="SELECT e FROM EventContent e")
public class EventContent implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="event_content_no")
	@ApiModelProperty(notes = "일련번호" , hidden = true)
	private int eventContentNo;

	@ApiModelProperty(notes = "사용자 식별(쿠키정보로 식별)" , hidden = true , required = true)
	@Column(name="userHash")
	private String userHash;

	@ApiModelProperty(notes = "Event 상세 제목" , required = true)
	@Column(name="title")
	private String title;

	@ApiModelProperty(notes = "Event 상세", required = true)
	@Column(name="event_desc")
	private String eventDesc;

    @ApiModelProperty(notes = "Event 시작일")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="event_start")
	private Date eventStart;

    @ApiModelProperty(notes = "Event 종료일")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="event_end")
    private Date eventEnd;

	@ApiModelProperty(notes = "행사일정 반복 방법")
	@Column(name="repeat_kind")
    @Enumerated(EnumType.STRING)
	private Codes.EV_REPKIND repeatKind;

	@ApiModelProperty(notes = "Event Path-관련 URL")
	@Column(name="path")
	private String refPath;

//	// 행사 위치들..~
    @Transient
	@ApiModelProperty(notes = "위치정보들" )
	private List<EventLocation> eventLocations;

	@ApiModelProperty(notes = "관심 태그")
	@Column(name="tags")
	private String tags;

	@ApiModelProperty(notes = "등록상태" , required = true)
	@Column(name="stat")
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