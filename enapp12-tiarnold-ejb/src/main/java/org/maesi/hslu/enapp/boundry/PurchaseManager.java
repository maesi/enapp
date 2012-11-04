package org.maesi.hslu.enapp.boundry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.maesi.hslu.enapp.control.PurchaseFacade;
import org.maesi.hslu.enapp.control.PurchaseItemFacade;
import org.maesi.hslu.enapp.dto.ProductDto;
import org.maesi.hslu.enapp.entity.Purchase;
import org.maesi.hslu.enapp.entity.Purchaseitem;

/**
 * Session Bean implementation class PurchaseManager
 */
@Stateless
@LocalBean
public class PurchaseManager {

	@Inject
	private ProductManager productManager;
	
    public void add(Integer aUser, Map<Integer, Integer> aItems) {
    	Integer _purchaseId = savePurchase(aUser);
    	for(Integer _productId : aItems.keySet()) {
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
	
	private void savePurchaseItem(Integer aPurchase, Integer aProduct, BigDecimal aQuantity) {
		Purchaseitem _purchaseItem = new Purchaseitem();
		_purchaseItem.setPurchaseid(aPurchase);
		_purchaseItem.setProductid(aProduct);
		_purchaseItem.setQuantity(aQuantity);
		
		ProductDto _productDto = productManager.get(aProduct);
		
		_purchaseItem.setUnitprice(_productDto.getUnitprice());
		_purchaseItem.setLineamount(_productDto.getUnitprice().multiply(aQuantity));
		
		PurchaseItemFacade _facade = new PurchaseItemFacade();
		_facade.create(_purchaseItem);
	}

}
