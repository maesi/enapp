package org.maesi.hslu.enapp.control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	

	public List<Purchase> findAllByUserId(Integer aUserId) {
		Query _query = em.createNamedQuery("purchase.findAllByUserId",
				Purchase.class);
		_query.setParameter("userId", aUserId);
		return _query.getResultList();
	}

}
