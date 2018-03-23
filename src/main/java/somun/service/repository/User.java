package somun.service.repository;

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
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_no")
	@ApiModelProperty(notes = "자동생성일련번호" , hidden = true)
	private Integer userNo;

	@Column(name="user_desc")
	private String userDesc;

	@Column(name="user_id")
	@ApiModelProperty(notes = "사용자 사용 ID", required = true)
	private String userId;

	@Column(name="user_nm")
	@ApiModelProperty(notes = "사용자명", required = true)
	private String userNm;

	@Column(name= "user_provider")
	@ApiModelProperty(notes = "사용자 제공 업체", required = true)
	private String userProvider;

	@Column(name="user_photos")
	@ApiModelProperty(notes = "사용자 사진 URL")
	private String userPhotos;

	@Column(name="user_stat")
	@ApiModelProperty(notes = "사용자 정보 상태" , required = true)
	@Enumerated(EnumType.STRING)
	private Codes.USER_STAT userStat;

    @Column(name="user_hash")
    @ApiModelProperty(notes = "사용자 unique hash 값" , required = true)
	private String userHash;

	@ApiModelProperty(notes = "생성일", hidden = true , required = true)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="create_dt")
	private Date createDt;

	@ApiModelProperty(notes = "생성자", hidden = true , required = true)
	@Column(name="create_no")
	private Integer createNo;

	@ApiModelProperty(notes = "수정일", hidden = true )
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="update_dt")
	private Date updateDt;

	@ApiModelProperty(notes = "수정자", hidden = true )
	@Column(name="update_no")
	private Integer updateNo;




}

