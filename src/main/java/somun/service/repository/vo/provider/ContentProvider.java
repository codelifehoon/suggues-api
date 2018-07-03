package somun.service.repository.vo.provider;

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

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import somun.common.biz.Codes;

/**
 * The persistent class for the address database table.
 * 
 */
@Data
@Builder
@Slf4j
@Entity
@Table(name="content_provider")
@NamedQuery(name="ContentProvider.findAll", query="SELECT a FROM ContentProvider a")

public class ContentProvider implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="seq")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int seq;

	@Column(name="provider")
	@Enumerated(EnumType.STRING)
	private Codes.CONTPROV provider;

	@Column(name="provider_key")
	private String providerKey;

	@Column(name = "contet_comb",columnDefinition = "json")
	private String contetComb;

	@Column(name="stat")
	@Enumerated(EnumType.STRING)
	private Codes.CONTPROV_STAT stat;

	@Column(name="provider_modifiedtime")
	private String providerModifiedtime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="create_dt")
	private Date createDt;

	@Column(name="create_no")
	private Integer createNo;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="update_dt")
	private Date updateDt;

	@Column(name="update_no")
	private Integer updateNo;


}