package main.java.org.maesi.hslu.enapp;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.maesi.hslu.enapp.boundry.CustomerManager;
import org.maesi.hslu.enapp.dto.CustomerDto;

@SessionScoped
public class AccountBean {

	@Inject 
	CustomerManager customerManager;
	
	private CustomerDto customer = new CustomerDto();
		
	public String login() {
		customerManager.login(customer);
		if(isLoggedIn()) {
			return "index";	
		}
		return "login";
	}
	
	public String logout() {
		setCustomer(new CustomerDto());
		return "index";	
	}

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}	
	
	public boolean isLoggedIn() {
		return customer.getId() > 0;
	}
}
