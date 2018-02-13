package myjpa.repository;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the hobby database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name="hobby")
@NamedQuery(name="Hobby.findAll", query="SELECT h FROM Hobby h")
public class Hobby implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="hobby_seq")
	@ApiModelProperty(notes = "자동생성일련번호" , hidden = true)
	private int hobbySeq;

	@Column(name="hobby_desc")
	private String hobbyDesc;

	@Column(name="hobby_kind")
	private String hobbyKind;

	@Column(name="user_no")
	private int userNo;



}