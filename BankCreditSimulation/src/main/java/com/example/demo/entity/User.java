package com.example.demo.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="T_User")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 private long userId ;
	 private String firstName ;
	 private String lastName ;
	 private String email;
	 private String password;
	
	 @Enumerated(EnumType.STRING)
	 private Role role;
	 
	 @Temporal(TemporalType.DATE)
	 private Date dateNaissance;
	 
	 @JsonIgnore
	 @ManyToMany(mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
		private List<Agency> agency;
	 

	public List<Agency> getAgency() {
		return agency;
	}

	public void setAgency(List<Agency> agency) {
		this.agency = agency;
	}

	public long getId() {
		return userId;
	}

	public void setId(long id) {
		this.userId = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	  
	
	 
	
	
}

