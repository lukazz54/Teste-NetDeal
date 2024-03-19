package br.com.netdeal.userregister.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String password;
	
	@Column(name="hierarchical_user")
	private Long idHierarchical;
	
	@Column(name="password_score")
	private int passwordScore;
	
	@Column(name="password_weight")
	private String passwordWeight;
	
	
}
