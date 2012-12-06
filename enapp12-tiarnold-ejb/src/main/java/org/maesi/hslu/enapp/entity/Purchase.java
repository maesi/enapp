package org.maesi.hslu.enapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the purchase database table.
 * 
 */
@Entity
@Table(name="purchase")
@NamedQueries({ @NamedQuery(name = "purchase.findAllByUserId", query = "SELECT p FROM Purchase p  WHERE p.customerid = :userId")})
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable=false)
	private Integer customerid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date datetime;

	@Column(length=15)
	private String status;

	@Column(precision=10)
	private BigDecimal totalamount;
	
	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	@Column
	private int paymentid;

	public Purchase() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}

	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(int paymentid) {
		this.paymentid = paymentid;
	}

}