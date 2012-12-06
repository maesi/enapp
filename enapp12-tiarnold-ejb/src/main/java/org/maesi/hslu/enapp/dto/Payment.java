package org.maesi.hslu.enapp.dto;

import java.math.BigDecimal;

public class Payment {

	private int purchaseId;
	private int customerId;
	private BigDecimal amount;
	private String creditCardNumber;
	private String creditCardCvc;
	private String creditCardExpire;

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardCvc() {
		return creditCardCvc;
	}

	public void setCreditCardCvc(String creditCardCvc) {
		this.creditCardCvc = creditCardCvc;
	}

	public String getCreditCardExpire() {
		return creditCardExpire;
	}

	public void setCreditCardExpire(String aCreditCardExpire) {
		this.creditCardExpire = aCreditCardExpire;
	}
}
