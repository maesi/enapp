package org.maesi.hslu.enapp;

import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.maesi.hslu.enapp.boundry.PurchaseManager;
import org.maesi.hslu.enapp.dto.CreditCardDto;
import org.maesi.hslu.enapp.dto.PurchaseDto;

@SessionScoped
public class PurchaseBean {

	@Inject 
	PurchaseManager purchaseManager;

	@Inject
	AccountBean accountBean;

	@Inject
	ItemsBean itemsBean;
	
	private CreditCardDto creditCard = new CreditCardDto();

	public List<PurchaseDto> getList() {
		Integer _id = accountBean.getCustomer().getId();
		return purchaseManager.getPurchase(_id);
	}
	
	public void createPurchase() {
		
		purchaseManager.create(accountBean.getCustomer().getId(), itemsBean.getItems(), creditCard);
	}
	
	public CreditCardDto getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCardDto creditCard) {
		this.creditCard = creditCard;
	}
}
