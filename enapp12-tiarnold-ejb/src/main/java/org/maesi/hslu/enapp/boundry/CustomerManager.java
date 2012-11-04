package org.maesi.hslu.enapp.boundry;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.maesi.hslu.enapp.control.CustomerFacade;
import org.maesi.hslu.enapp.dto.CustomerDto;
import org.maesi.hslu.enapp.entity.Customer;

/**
 * Session Bean implementation class CustomerManager
 */
@Stateless
@LocalBean
public class CustomerManager {
    
    public void login(CustomerDto aCustomer) {
    	CustomerFacade _facade = new CustomerFacade();
    	Customer _entity = _facade.isLoginCorrect(aCustomer.getName(), aCustomer.getPassword());
    	if(_entity != null) {
    		aCustomer.setId(_entity.getId());
    	}
    }

	private CustomerDto entityToDto(Customer _entity) {
		if(_entity == null) {
			return null;
		}
		CustomerDto _return = new CustomerDto();
		_return.setId(_entity.getId());
		_return.setName(_entity.getUsername());

		return _return;
	}

}
