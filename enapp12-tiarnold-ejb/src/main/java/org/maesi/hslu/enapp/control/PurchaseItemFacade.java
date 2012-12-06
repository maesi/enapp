package org.maesi.hslu.enapp.control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.maesi.hslu.enapp.entity.Purchase;
import org.maesi.hslu.enapp.entity.Purchaseitem;

public class PurchaseItemFacade extends AbstractFacade<Purchaseitem> {

	private EntityManager em = javax.persistence.Persistence.createEntityManagerFactory("enapp12-tiarnold-pu").createEntityManager();

	public PurchaseItemFacade() {
		super(Purchaseitem.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	
	public List<Purchaseitem> findAllByPurchaseId(Integer aPurchsesId) {
		Query _query = em.createNamedQuery("purchase.findAllByPurchaseId",
				Purchaseitem.class);
		_query.setParameter("purchaseId", aPurchsesId);
		return _query.getResultList();
	}
}
