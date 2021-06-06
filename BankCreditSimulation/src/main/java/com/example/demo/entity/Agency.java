package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "agencies")
public class Agency implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String agencyName;
	private String city;
	private String adress;
	private long contact;
	private String email;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> user;

	

	public List<User> getUser() {
		return user;
	}


	public void setUser(List<User> user) {
		this.user = user;
	}


	public Agency() {
	}
	
	
	public Agency(long id, String agencyName, String city, String adress, long contact, String email) {
		
		this.id = id;
		this.agencyName = agencyName;
		this.city = city;
		this.adress = adress;
		this.contact = contact;
		this.email = email;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Agency [id=" + id + ", agencyName=" + agencyName + ", city=" + city + ", adress=" + adress
				+ ", contact=" + contact + ", email=" + email + ", user=" + user + "]";
	}
	
	

}
