package org.maesi.hslu.enapp.control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.maesi.hslu.enapp.entity.Product;

@Stateless
public class ProductFacade extends AbstractFacade<Product> {

	//@PersistenceContext(unitName = "enapp12-tiarnold-pu")
    private EntityManager em = javax.persistence.Persistence.createEntityManagerFactory("enapp12-tiarnold-pu").createEntityManager();

	public ProductFacade() {
		super(Product.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
}