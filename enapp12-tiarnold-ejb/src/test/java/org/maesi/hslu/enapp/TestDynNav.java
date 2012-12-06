package org.maesi.hslu.enapp;

import org.junit.Assert;
import org.junit.Test;
import org.maesi.hslu.enapp.control.DynNavFacade;

public class TestDynNav {

	@Test
	public void doIt() {
		DynNavFacade _facade = new DynNavFacade();
		Assert.assertTrue(_facade.findAll().size() > 1);		
	}
	
}
