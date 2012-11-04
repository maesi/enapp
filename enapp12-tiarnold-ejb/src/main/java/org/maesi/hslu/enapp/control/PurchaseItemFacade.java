package org.maesi.hslu.enapp.control;

import javax.persistence.EntityManager;

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

}
