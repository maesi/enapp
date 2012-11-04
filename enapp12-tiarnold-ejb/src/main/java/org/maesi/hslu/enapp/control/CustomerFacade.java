package org.maesi.hslu.enapp.control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
		_query.setParameter("password", md5(aPassword));
		if(_query.getResultList().size() == 1) {
			return (Customer)_query.getResultList().get(0);
		}
		return null;
	}

	private String md5(String aPassword) {
		MessageDigest _messageDigest;
		try {
			_messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return aPassword;
		}
		byte[] hash = _messageDigest.digest(aPassword.getBytes());

		StringBuffer _stringBuffer = new StringBuffer();
		for (int i = 0; i < hash.length; ++i) {
			_stringBuffer.append(Integer.toHexString((hash[i] & 0xFF) | 0x100)
					.toLowerCase().substring(1, 3));
		}
		return _stringBuffer.toString();
	}
}
