package org.maesi.hslu.enapp;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.maesi.hslu.enapp.control.CustomerFacade;
import org.maesi.hslu.enapp.entity.Customer;

public class TestMaven {
	
	@Test
	public void simpleAdd() {

//		Customer _c = new Customer();
//		_c.setAddress("Alte Kantonsstrasse 39");
//		_c.setEmail("marcel.arnold@gmx.ch");
//		_c.setName("Marcel Arnol");
//		_c.setPassword("1234");
//		_c.setUsername("mvb");
//		
//		CustomerFacade _cf = new CustomerFacade();
//		_cf.create(_c);
		
		Integer _i = new Integer(23);
		assertEquals(_i, new Integer(23));
	}
}
