package org.maesi.hslu.enapp;

import java.util.List;

import javax.ejb.FinderException;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.maesi.hslu.enapp.boundry.CustomerManager;
import org.maesi.hslu.enapp.dto.CreditCardDto;
import org.maesi.hslu.enapp.dto.CustomerDto;

@SessionScoped
public class CustomerBean {

	@Inject 
	CustomerManager customerManager;


	
	public List<CustomerDto> getList() {
		return customerManager.getCustomerList();
	}
	

	public CustomerDto getCustomer() {
		return customerManager.getById(customerId);
	}
	
	private int customerId;
	private String navisionId;
	
	
	public String getNavisionId() {
		return navisionId;
	}


	public void setNavisionId(String navisionId) {
		this.navisionId = navisionId;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void update() {
		customerManager.setNavisionId(customerId, navisionId);
	}
	

}
