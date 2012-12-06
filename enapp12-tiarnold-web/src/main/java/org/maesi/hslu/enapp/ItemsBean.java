package org.maesi.hslu.enapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.maesi.hslu.enapp.boundry.PurchaseManager;

@SessionScoped
public class ItemsBean {

	@Inject 
	PurchaseManager purchaseManager;
	
	private Map<String, Integer> items;
	
	public ItemsBean() {
		clearBasketInternal();
	}
	
	
	public Map<String, Integer> getItems() {
		return items;
	}
	
	public Set<String> getItemKeys() {
		return items.keySet();
	}
		
	public String checkout() {
		purchaseManager.add(customer, items);
		clearBasketInternal();
		return "index";
	}
	
	public String clearBasket() {
		clearBasketInternal();
		return "basket";
	}
	
	private void clearBasketInternal() {
		items = new HashMap<String, Integer>();
	}
	
	public String addFromProducts() {
		addInternal();
		return "products";
	}
	
	public void addInternal() {
		Integer _value = items.get(product);
		if(_value == null) {
			_value = 1;
		} else {
			_value ++;
		}
		items.put(product, _value);
	}
	
	public String add() {
		addInternal();
		return "basket";
	}
	
	public String remove() {
		removeInternal();
		return "basket";
	}
	
	private void removeInternal() {
		Integer _value = items.get(product);
		if(_value == null || _value == 0) {
			items.remove(product);
		} else {
			_value --;
			items.put(product, _value);
		}
	}

	private String product;
	public void setProduct(String product) {
		this.product = product;
	}
	
	private Integer customer;
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
	
}
