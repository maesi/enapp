package org.maesi.hslu.enapp.boundry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.maesi.hslu.enapp.control.DynNavFacade;
import org.maesi.hslu.enapp.dto.ProductDto;
import org.maesi.hslu.enapp.ext.dynnav.Item;

@Stateless
@LocalBean
public class ProductManager {

	@Inject
	DynNavFacade dynNav;
	
	public List<ProductDto> getList() {
		List<ProductDto> _return = new ArrayList<ProductDto>();
		
		getDynNavProducts(_return);
			
		return _return;	
	}

	private void getDynNavProducts(List<ProductDto> _return) {
		Collection<Item> _items = dynNav.findAll();
		for(Item _item : _items) {
			_return.add(entityToDto(_item));
		}
	}
	
	public ProductDto get(String aId) {				
		return entityToDto(dynNav.find(aId));	
	}

	private ProductDto entityToDto(Item aItem) {
		ProductDto _dto = new ProductDto();
		_dto.setId(aItem.getNo());
		_dto.setName(aItem.getDescription());
		_dto.setUnitprice(aItem.getUnitPrice());	
		return _dto;
	}

}
