package com.bs.beans;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
//@Table(uniqueConstraints=@UniqueConstraint(columnNames={"stockId"}))
public class PurchasedStock {
	
	//You don't have sequences in Mysql, but you can use the 'TABLE' id generation
	@Id
	@SequenceGenerator(name = "PURCHASED_STOCK_ID_GENERATOR",sequenceName = "purchased_stock_sequence",allocationSize = 1,initialValue = 6)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PURCHASED_STOCK_ID_GENERATOR")  //For h2 & Oracle
	//@TableGenerator( name = "PSTOCK_GEN",table = "PSTOCK_GENERATOR_TABLE", initialValue = 6,allocationSize = 1) //mysql 
	//@GeneratedValue(strategy = GenerationType.TABLE,generator = "PSTOCK_GEN")
	private int id;
	private double totalUnits;
	private BigDecimal totalPrice;
	@ManyToOne
	private User user;
	
	private int stockId;
	public PurchasedStock() {
		super();
	}
	public PurchasedStock(int id, double totalUnits, BigDecimal totalPrice, User user, int stockId) {
		super();
		this.id = id;
		this.totalUnits = totalUnits;
		this.totalPrice = totalPrice;
		this.user = user;
		this.stockId = stockId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getTotalUnits() {
		return totalUnits;
	}
	public void setTotalUnits(double totalUnits) {
		this.totalUnits = totalUnits;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	@Override
	public String toString() {
		return "PurchasedStock [id=" + id + ", totalUnits=" + totalUnits + ", totalPrice=" + totalPrice + ", user="
				+ user + ", stockId=" + stockId + "]";
	}
	public PurchasedStock(double totalUnits, BigDecimal totalPrice, User user, int stockId) {
		super();
		this.totalUnits = totalUnits;
		this.totalPrice = totalPrice;
		this.user = user;
		this.stockId = stockId;
	}	
}
