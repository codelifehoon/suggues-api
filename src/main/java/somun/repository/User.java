package somun.repository;

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
	private int userNo;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="create_dt")
 	private Date createDt;

	@Column(name="create_no")
	private int createNo;

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
	@ApiModelProperty(notes = "사용자 정보 상태")
	private String userStat;

    @Column(name="user_hash")
    @ApiModelProperty(notes = "사용자 unique hash 값")
    private String userHash;



//	@OneToOne(fetch = FetchType.LAZY , cascade={CascadeType.ALL})
//	@JoinColumn(name="user_no")
//	private Address address;



}

