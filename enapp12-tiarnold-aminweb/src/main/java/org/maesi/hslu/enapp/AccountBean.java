package org.maesi.hslu.enapp;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.maesi.hslu.enapp.boundry.CustomerManager;
import org.maesi.hslu.enapp.dto.CustomerDto;

@SessionScoped
public class AccountBean {

	@Inject 
	CustomerManager customerManager;

	private CustomerDto customer;
	
	public String logout() {
		String result="/index?faces-redirect=true";
	     
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
	     
	    try {
	      request.logout();
	      customer = null;
	    } catch (ServletException e) {
	      result = "/loginError?faces-redirect=true";
	    }
	     
	    return result;
	}

	public CustomerDto getCustomer() {
		if(customer == null && isLoggedIn()) {
			String _username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
			customer = customerManager.getByName(_username);
		}
		return customer;
	}
	
	
	public String save() {
		customerManager.update(customer);
		return "";	
	}
	
	
	public boolean isLoggedIn() {
		return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null;
	}
}
