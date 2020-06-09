package com.bs.beans;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int tradeId;
	@CheckTradeTypes
	private String type;
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	private String symbol;
	@Max(30)
	@Min(10)
	private int shares;
	@DecimalMin(value = "130.42")
	@DecimalMax(value = "195.65")
	private BigDecimal price;
	@Pattern(regexp = BrokerageConstants.TIMESTAMP_FORMAT_REGEX)
	private String timestamp;

	public Trade() {
		super();
	}

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", type=" + type + ", user=" + user + ", symbol=" + symbol + ", shares="
				+ shares + ", price=" + price + ", timestamp=" + timestamp + "]";
	}

	public Trade(int tradeId, String type, User user, String symbol, @Max(30) @Min(10) int shares,
			@DecimalMin("130.42") @DecimalMax("195.65") BigDecimal price, String timestamp) {
		super();
		this.tradeId = tradeId;
		this.type = type;
		this.user = user;
		this.symbol = symbol;
		this.shares = shares;
		this.price = price;
		this.timestamp = timestamp;
	}
	
}
