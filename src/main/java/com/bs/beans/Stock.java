package com.bs.beans;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stock {
	String symbol;
	BigDecimal highest;
	BigDecimal lowest;

	public Stock() {
		super();
	}

	public Stock(String symbol, BigDecimal highest, BigDecimal lowest) {
		super();
		this.symbol = symbol;
		this.highest = highest;
		this.lowest = lowest;
	}

}
