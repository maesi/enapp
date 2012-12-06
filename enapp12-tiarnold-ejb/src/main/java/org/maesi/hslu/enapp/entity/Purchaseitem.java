package org.maesi.hslu.enapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the purchaseitem database table.
 * 
 */
@Entity
@Table(name="purchaseitem")
@NamedQueries({ @NamedQuery(name = "purchase.findAllByPurchaseId", query = "SELECT p FROM Purchaseitem p WHERE p.purchaseid = :purchaseId")})
public class Purchaseitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=90)
	private String description;

	@Column(precision=10)
	private BigDecimal lineamount;

	@Column(nullable=false, length=30)
	private String productid;

	@Column(nullable=false)
	private int purchaseid;

	@Column(precision=10)
	private BigDecimal quantity;

	@Column(precision=10)
	private BigDecimal unitprice;

	public Purchaseitem() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getLineamount() {
		return this.lineamount;
	}

	public void setLineamount(BigDecimal lineamount) {
		this.lineamount = lineamount;
	}

	public String getProductid() {
		return this.productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public int getPurchaseid() {
		return this.purchaseid;
	}

	public void setPurchaseid(int purchaseid) {
		this.purchaseid = purchaseid;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitprice() {
		return this.unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

}