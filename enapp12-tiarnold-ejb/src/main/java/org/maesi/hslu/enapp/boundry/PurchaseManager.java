package org.maesi.hslu.enapp.boundry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.maesi.hslu.enapp.control.PostfinanceFacade;
import org.maesi.hslu.enapp.control.PurchaseFacade;
import org.maesi.hslu.enapp.control.PurchaseItemFacade;
import org.maesi.hslu.enapp.dto.CreditCardDto;
import org.maesi.hslu.enapp.dto.Payment;
import org.maesi.hslu.enapp.dto.ProductDto;
import org.maesi.hslu.enapp.dto.PurchaseDto;
import org.maesi.hslu.enapp.entity.Purchase;
import org.maesi.hslu.enapp.entity.Purchaseitem;
import org.maesi.hslu.enapp.ext.postfinance.PaymentPostFinance;

/**
 * Session Bean implementation class PurchaseManager
 */
@Stateless
@LocalBean
public class PurchaseManager {

	@Inject
	private ProductManager productManager;
	
	@Inject
	private PaymentPostFinance postFinance;
	
    public void add(Integer aUser, Map<String, Integer> aItems) {
    	Integer _purchaseId = savePurchase(aUser);
    	for(String _productId : aItems.keySet()) {
    		savePurchaseItem(_purchaseId, _productId, new BigDecimal(aItems.get(_productId)));
    	}
    }

	private Integer savePurchase(Integer aUser) {
		Purchase _purchase = new Purchase();
		_purchase.setDatetime(new Date());
		_purchase.setStatus("open");
		_purchase.setCustomerid(aUser);
		
		PurchaseFacade _facade = new PurchaseFacade();
		_facade.create(_purchase);
		return _purchase.getId();		
	}
	
	 
	
	 private void updatePaymentId(Integer aId, Integer aPaymentId) {
			PurchaseFacade _facade = new PurchaseFacade();
			Purchase _pur = _facade.find(aId);
			_pur.setPaymentid(aPaymentId);
			_facade.edit(_pur);
		} 
	 
	private void updatePurchaseState(Integer aId, String aState) {
		PurchaseFacade _facade = new PurchaseFacade();
		Purchase _pur = _facade.find(aId);
		_pur.setStatus(aState);
		_facade.edit(_pur);
	}
	
	private BigDecimal savePurchaseItem(Integer aPurchase, String aProduct, BigDecimal aQuantity) {
		Purchaseitem _purchaseItem = new Purchaseitem();
		_purchaseItem.setPurchaseid(aPurchase);
		_purchaseItem.setProductid(aProduct);
		_purchaseItem.setQuantity(aQuantity);
		
		ProductDto _productDto = productManager.get(aProduct);
		
		_purchaseItem.setUnitprice(_productDto.getUnitprice());
		BigDecimal _lineamount = _productDto.getUnitprice().multiply(aQuantity);
		_purchaseItem.setLineamount(_lineamount);
		
		PurchaseItemFacade _facade = new PurchaseItemFacade();
		_facade.create(_purchaseItem);
		return _lineamount;
	}
	
	public List<PurchaseDto> getPurchase(Integer aUserId) {
		List<PurchaseDto> _return = new ArrayList<PurchaseDto>();
		
		PurchaseItemFacade _facade = new PurchaseItemFacade();
		List<Purchase> _entities = _facade.findAllByUserId(aUserId);
		for(Purchase _entity : _entities) {
			_return.add(entityToDto(_entity));
		}
		
		return _return;
	}

	private PurchaseDto entityToDto(Purchase _entity) {
		PurchaseDto _dto = new PurchaseDto();
		_dto.setDate(_entity.getDatetime());
		_dto.setState(_entity.getStatus());
		return _dto;
	}

	@Inject
	PostfinanceFacade pfFacade;
	
	public void create(int aCustomerId, Map<String, Integer> aItems,
			CreditCardDto aCreditCard) {

        Payment payment = new Payment();
        payment.setCustomerId(aCustomerId);
        payment.setCreditCardNumber(aCreditCard.getNumber());
        payment.setCreditCardCvc(aCreditCard.getVerificationNumber());
        
        payment.setCreditCardExpire(aCreditCard.getExpireDate());		
        
        Integer _purchaseId = savePurchase(aCustomerId);
        
        payment.setPurchaseId(_purchaseId);
        
        BigDecimal _amount = new BigDecimal(0);
        
        for(String _key : aItems.keySet()) {
        	_amount.add(savePurchaseItem(_purchaseId, _key, new BigDecimal(aItems.get(_key))));
        }
        
        _amount.add(savePurchaseItem(_purchaseId, "ART0000225", new BigDecimal(3)));
       
        payment.setAmount(_amount);
        
        
        
        try {
			int i = pfFacade.doPayment(payment);
			updatePaymentId(_purchaseId, i);
	        
	        updatePurchaseState(_purchaseId, "payed");
		} catch (Exception e) {
			System.out.println("Fehler beim Bestellvorgang: " + e.getMessage());
		}
        
        
		
	}

}
