package org.maesi.hslu.enapp.dto;

import org.maesi.hslu.enapp.helper.Md5Helper;


public class CustomerDto {

	private int id;
	
	private String name;

	private String password;

	private String navisionid;
	
	private String realname;
	private String adress;
	private String email;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(password == null || password.isEmpty()) {
			return;
		}
		setPassword(password, true);
	}
	public void setPassword(String password, boolean aMakeMd5) {
		this.password = Md5Helper.md5(password);
	}
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNavisionid() {
		return navisionid;
	}

	public void setNavisionid(String navisionid) {
		this.navisionid = navisionid;
	}
}
