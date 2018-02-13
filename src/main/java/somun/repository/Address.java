package somun.repository;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


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