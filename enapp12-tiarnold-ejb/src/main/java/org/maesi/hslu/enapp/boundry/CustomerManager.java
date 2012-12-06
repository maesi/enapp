package org.maesi.hslu.enapp.boundry;

import java.util.ArrayList;
import java.util.List;

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
    
	@Deprecated
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
		_return.setNavisionid(_entity.getNavisionid());
		_return.setPassword(_entity.getPassword(), false);
		_return.setRealname(_entity.getName());
		_return.setAdress(_entity.getAddress());
		_return.setEmail(_entity.getEmail());
		return _return;
	}

	public CustomerDto getByName(String aName) {
		CustomerFacade _facade = new CustomerFacade();
    	return entityToDto(_facade.findByUsername(aName));
	}

	public void update(CustomerDto aDto) {
		CustomerFacade _facade = new CustomerFacade();
		_facade.updateUser(dtoToEntity(aDto));
	}
	
	public void create(CustomerDto aDto) {
		CustomerFacade _facade = new CustomerFacade();
		_facade.create(dtoToEntity(aDto));
	}
	
	public void setNavisionId(Integer aUserId, String aNavisionId) {
		CustomerFacade _facade = new CustomerFacade();
		Customer _cust = _facade.find(aUserId);
		_cust.setNavisionid(aNavisionId);
		_facade.edit(_cust);
	}
	
	private Customer dtoToEntity(CustomerDto _dto) {
		if(_dto == null) {
			return null;
		}
		Customer _return = new Customer();
		_return.setId(_dto.getId());
		_return.setUsername(_dto.getName());
		_return.setPassword(_dto.getPassword());
		_return.setNavisionid(_dto.getNavisionid());
		_return.setName(_dto.getRealname());
		_return.setAddress(_dto.getAdress());
		_return.setEmail(_dto.getEmail());
		return _return;
	}
	
	public List<CustomerDto> getCustomerList() {
		List<CustomerDto> _return = new ArrayList<CustomerDto>();
		
		CustomerFacade _facade = new CustomerFacade();
		List<Customer> _customers = _facade.findAll();
		for(Customer _cust : _customers) {
			_return.add(entityToDto(_cust));
		}
		return _return;
	}
	
	public CustomerDto getById(Integer aId) {
		CustomerFacade _facade = new CustomerFacade();
    	return entityToDto(_facade.find(aId));
	}

}
