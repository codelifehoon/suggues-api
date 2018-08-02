package somun.service.repository.vo.content;

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

import org.hibernate.annotations.DynamicUpdate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name="content_storage")
@DynamicUpdate(value=true)
@NamedQuery(name="ContentStorage.findAll", query="SELECT e FROM ContentStorage e")
public class ContentStorage {

//activityRefNo , activityCode ,storageCode,storageStat
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="content_storage_no")
    @ApiModelProperty(notes = "일련번호" , hidden = true)
    private Integer contentStorageNo;

    @ApiModelProperty(notes = "관련 컨텐츠 코드")
    @Column(name="activity_code")
    @Enumerated(EnumType.STRING)
    private Codes.ACTIVITY_CODE activityCode;

    @ApiModelProperty(notes = "관련 컨텐츠 일련번호")
    @Column(name="activity_ref_no")
    private Integer activityRefNo;

    @ApiModelProperty(notes = "storage 종류 코드" )
    @Column(name="storage_code")
    @Enumerated(EnumType.STRING)
    private Codes.STORAGE_CODE storageCode;

    @ApiModelProperty(notes = "cloude url path")
    @Column(name="storage_value" , length=10000)
    private String storageValue;

    @ApiModelProperty(notes = "cloude local path")
    @Column(name="full_path" , length=10000)
    private String fullPath;

    @ApiModelProperty(notes = "상태")
    @Column(name="stat")
    @Enumerated(EnumType.STRING)
    private Codes.STORAGE_STAT stat;

    @ApiModelProperty(notes = "셍성자")
    @Column(name="create_no")
    private Integer createNo;

    @ApiModelProperty(notes = "생성일")
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
