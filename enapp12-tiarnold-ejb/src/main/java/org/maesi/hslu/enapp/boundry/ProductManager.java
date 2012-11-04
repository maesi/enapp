package org.maesi.hslu.enapp.boundry;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.maesi.hslu.enapp.control.ProductFacade;
import org.maesi.hslu.enapp.dto.ProductDto;
import org.maesi.hslu.enapp.entity.Product;

@Stateless
@LocalBean
public class ProductManager {

	public List<ProductDto> getList() {
		List<ProductDto> _return = new ArrayList<ProductDto>();
		
		ProductFacade _facade = new ProductFacade();		
		List<Product> _productEntities = _facade.findAll();
		
		for(Product _productEntity: _productEntities) {
			_return.add(entityToDto(_productEntity));
		}
			
		return _return;	
	}
	
	public ProductDto get(Integer aId) {		
		ProductFacade _facade = new ProductFacade();		
		Product _productEntity = _facade.find(aId);
			
		return entityToDto(_productEntity);	
	}

	private ProductDto entityToDto(Product aEntity) {
		ProductDto aDto = new ProductDto();
		aDto.setId(aEntity.getId());
		aDto.setName(aEntity.getName());
		aDto.setUnitprice(aEntity.getUnitprice());		
		return aDto;
	}

}
