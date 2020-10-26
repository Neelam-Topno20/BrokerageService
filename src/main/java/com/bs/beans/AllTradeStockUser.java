package com.bs.beans;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AllTradeStockUser {
	@Id
	private int tradeId;
	
	private String type;
	
	private String symbol;
	
	private int shares;

	private BigDecimal price;

	private String timestamp;
	
	private String userName;
	
	private String emailId;
	
	private boolean active;
	
	private String roles;
	
	private String stockName;
	
	private double ppu;
		

	public AllTradeStockUser() {
		super();
	}
	
	
	
}
