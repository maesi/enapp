package org.maesi.hslu.enapp.control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.maesi.hslu.enapp.entity.Customer;

@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

	// @PersistenceContext(unitName = "enapp12-tiarnold-pu")
	private EntityManager em = Persistence.createEntityManagerFactory(
			"enapp12-tiarnold-pu").createEntityManager();

	public CustomerFacade() {
		super(Customer.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public Customer isLoginCorrect(String aName, String aPassword) {
		Query _query = em.createNamedQuery("customer.findByNameAndPassword",
				Customer.class);
		_query.setParameter("username", aName);
		_query.setParameter("password", aPassword);
		if(_query.getResultList().size() == 1) {
			return (Customer)_query.getResultList().get(0);
		}
		return null;
	}
	
	public Customer findByUsername(String aUsername) {
		Query _query = em.createNamedQuery("customer.findByUsername",
				Customer.class);
		_query.setParameter("username", aUsername);
		if(_query.getResultList().size() == 1) {
			return (Customer)_query.getResultList().get(0);
		}
		return null;
	}
	
	public void updateUser(Customer aCust) {
		em.getTransaction().begin();
		em.merge(aCust);
		em.getTransaction().commit();
	}

	
}
