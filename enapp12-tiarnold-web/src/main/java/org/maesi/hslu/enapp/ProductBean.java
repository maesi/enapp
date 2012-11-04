package org.maesi.hslu.enapp;

import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import org.maesi.hslu.enapp.boundry.ProductManager;
import org.maesi.hslu.enapp.dto.ProductDto;

@RequestScoped
public class ProductBean {

	@Inject
	ProductManager pm;

	public List<ProductDto> getList() {
		return pm.getList();
	}

	public ProductDto getProduct() {
		Integer aId = 1;
		return pm.get(aId);
	}
}
