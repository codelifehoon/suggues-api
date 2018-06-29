package somun.service.repository.user;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import somun.service.repository.content.Hobby;

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
@Table(name="address")
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_no")
	@ApiModelProperty(notes = "사용자번호" , hidden = true)
	private int userNo;

	@Column(name="add_detail")
	private String addDetail;

	private String add2;

	private String addr1;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="user_no")
	private Collection<Hobby> hobbs;


}