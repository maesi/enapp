package org.maesi.hslu.enapp.boundry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.maesi.hslu.enapp.control.CustomerFacade;
import org.maesi.hslu.enapp.control.EnappDeamonFacade;
import org.maesi.hslu.enapp.control.PostfinanceFacade;
import org.maesi.hslu.enapp.control.PurchaseFacade;
import org.maesi.hslu.enapp.control.PurchaseItemFacade;
import org.maesi.hslu.enapp.dto.CreditCardDto;
import org.maesi.hslu.enapp.dto.Payment;
import org.maesi.hslu.enapp.dto.ProductDto;
import org.maesi.hslu.enapp.dto.PurchaseDto;
import org.maesi.hslu.enapp.entity.Customer;
import org.maesi.hslu.enapp.entity.Purchase;
import org.maesi.hslu.enapp.entity.Purchaseitem;
import org.maesi.hslu.enapp.ext.enappdeamon.SalesOrder;

/**
 * Session Bean implementation class PurchaseManager
 */
@Stateless
@LocalBean
public class PurchaseManager {

	@Inject
	private ProductManager productManager;
		
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
	
	private void updatePaymentTotalAmount(Integer aId, BigDecimal aTotalAmount) {
		PurchaseFacade _facade = new PurchaseFacade();
		Purchase _pur = _facade.find(aId);
		_pur.setTotalamount(aTotalAmount);
		_facade.edit(_pur);
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
	
	private void updateCorrelationId(Integer aId, String aCorrId) {
		PurchaseFacade _facade = new PurchaseFacade();
		Purchase _pur = _facade.find(aId);
		_pur.setCorrelationid(aCorrId);
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
		
		PurchaseFacade _facade = new PurchaseFacade();
		List<Purchase> _entities = _facade.findAllByUserId(aUserId);
		for(Purchase _entity : _entities) {
			_return.add(entityToDto(_entity));
		}
		
		return _return;
	}

	private PurchaseDto entityToDto(Purchase _entity) {
		PurchaseDto _dto = new PurchaseDto();
		_dto.setId(_entity.getId());
		_dto.setDate(_entity.getDatetime());
		_dto.setState(_entity.getStatus());
		_dto.setCustomerId(_entity.getCustomerid());
		_dto.setCorrelationId(_entity.getCorrelationid());
		return _dto;
	}

	@Inject
	PostfinanceFacade pfFacade;

	@Inject
	EnappDeamonFacade daemonFacade;

	@Inject
	CustomerManager customerManager;
	
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
        
        _amount = _amount.add(savePurchaseItem(_purchaseId, "ART0000225", new BigDecimal(3)));
        
        updatePaymentTotalAmount(_purchaseId, _amount);
        
        System.out.println("Amount : " + _amount.toString());
        payment.setAmount(_amount);
        System.out.println("PaymentAmount : " + payment.getAmount());
        
        
        
        try {
			int i = pfFacade.doPayment(payment);
			System.out.println("Payment-ID: " + i);
			updatePaymentId(_purchaseId, i);
			System.out.println("Payment-ID updated");
	        
	        updatePurchaseState(_purchaseId, "payed");
			System.out.println("Payment-Status updated");
		} catch (Exception e) {
			System.out.println("Fehler beim Bestellvorgang: " + e.getMessage());
		}
        
        PurchaseFacade _facade = new PurchaseFacade();
		Purchase _pur = _facade.find(_purchaseId);
        String _correlationId = daemonFacade.sendPurchase(_pur);

		System.out.println("CorrelationId: " + _correlationId);
		updateCorrelationId(_purchaseId, _correlationId);
        
		updateOrderState(_purchaseId);
	}

	public void updateOrderState(Integer aPurchaseId) {
		try {
			PurchaseFacade _facade = new PurchaseFacade();
			Purchase _pur = _facade.find(aPurchaseId);
					
			SalesOrder _order = daemonFacade.getOrderState(_pur.getCorrelationid());
			if(_order.getExternalCustomerId() != null && !_order.getExternalCustomerId().isEmpty()) {
				System.out.println("ExternaalCustomerId: " + _order.getExternalCustomerId());
				customerManager.setNavisionId(_pur.getCustomerid(), _order.getExternalCustomerId());
			} else {
				System.out.println("Empty ExternalCustomerId");
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Bestellungsupdate: " + e.getMessage());
		}
	}

}
