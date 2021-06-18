package edu.poly.shop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@Column(length = 30)
    private String username;
	@Column(length = 20,nullable = false)
    private String password;
	
	@Column(name = "fullname",length = 50,columnDefinition = "nvarchar(100) not null")
	private String fullname;

	
	@Column(name = "email",length = 50,columnDefinition = "nvarchar(100) not null")
	private String email;
	@Column(name = "roles" )
	private Boolean roles;
}
