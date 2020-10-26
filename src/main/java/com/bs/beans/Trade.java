package com.bs.beans;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.bs.constants.BrokerageConstants;
import com.bs.constraint.CheckTradeTypes;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Trade {
	
	//@TableGenerator( name = "TRADE_GEN",table = "TRADE_GENERATOR_TABLE", initialValue = 9,allocationSize = 1)
	//@GeneratedValue(strategy = GenerationType.TABLE,generator = "TRADE_GEN")
	@Id
	@SequenceGenerator(name = "TRADE_ID_GENERATOR", sequenceName = "trade_sequence", initialValue = 9,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRADE_ID_GENERATOR")  
	private int tradeId;
	@CheckTradeTypes
	private String type;
	//@ManyToOne
	//@JoinColumn(name = "userId")
	//private User user;
	private String symbol;
	
	@Max(value=10000,message="The no.of units ${validatedValue} must be less than or equal to 10000")
	@Min(value=10,message = "The no.of units ${validatedValue} must be greater than or equal to 10")
	private double shares;
	@DecimalMin(value = "500.0",message = "Minimum investing amount is 500 ")
	@DecimalMax(value = "1000000.0",message = "Maximum investing amount is 1000000")
	private BigDecimal price;
	@Pattern(regexp = BrokerageConstants.TIMESTAMP_FORMAT_REGEX)
	private String timestamp;

	public Trade() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tradeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (tradeId != other.tradeId)
			return false;
		return true;
	}

	public Trade(String type, String symbol,
			@Max(value = 10000, message = "The no.of units ${validatedValue} must be less than or equal to 10000") @Min(value = 10, message = "The no.of units ${validatedValue} must be greater than or equal to 10") double shares,
			@DecimalMin(value = "500.0", message = "Minimum investing amount is 500 ") @DecimalMax(value = "1000000.0", message = "Maximum investing amount is 1000000") BigDecimal price,
			@Pattern(regexp = "^\\d{4}-([0][0-9]|[1][0-2])-([0-2][0-9]|[3][0-1])\\s([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$") String timestamp) {
		super();
		this.type = type;
		this.symbol = symbol;
		this.shares = shares;
		this.price = price;
		this.timestamp = timestamp;
	}

	
	
	
}
