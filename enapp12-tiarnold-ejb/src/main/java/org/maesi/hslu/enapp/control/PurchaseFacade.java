package org.maesi.hslu.enapp.control;

import javax.persistence.EntityManager;

import org.maesi.hslu.enapp.entity.Purchase;

public class PurchaseFacade extends AbstractFacade<Purchase> {

	private EntityManager em = javax.persistence.Persistence.createEntityManagerFactory("enapp12-tiarnold-pu").createEntityManager();

	public PurchaseFacade() {
		super(Purchase.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
