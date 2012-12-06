package org.maesi.hslu.enapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@Table(name = "customer")
@NamedQueries({ @NamedQuery(name = "customer.findByNameAndPassword", query = "SELECT c FROM Customer c  WHERE c.username = :username AND c.password =:password"), 
		@NamedQuery(name = "customer.findByUsername", query = "SELECT c FROM Customer c  WHERE c.username = :username")})
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(length = 45)
	private String address;

	@Column(length = 90)
	private String email;

	@Column(length = 45)
	private String name;
	
	@Column(length = 32)
	private String navisionid;

	@Column(length = 32)
	private String password;

	@Column(nullable = false, length = 15)
	private String username;

	public Customer() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNavisionid() {
		return navisionid;
	}

	public void setNavisionid(String navisionid) {
		this.navisionid = navisionid;
	}

}