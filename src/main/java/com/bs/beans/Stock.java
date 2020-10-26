package com.bs.beans;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stock {

	
	
	@Id
	//@TableGenerator( name = "STOCK_GEN",table = "STOCK_GENERATOR_TABLE", initialValue = 7,allocationSize = 1)
	//@GeneratedValue(strategy = GenerationType.TABLE,generator = "STOCK_GEN")
	@SequenceGenerator(name = "STOCK_ID_GENERATOR", sequenceName = "stock_sequence",allocationSize = 1,initialValue = 7)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "STOCK_ID_GENERATOR")
	private int id;
	private String symbol;
	private String name;
	private double ppu;
	
	//Caused by: org.hibernate.TransientObjectException: object references an unsaved transient instance
	//- save the transient instance before flushing: com.bs.beans.Trade
	//requires cascade type because class contains one or more associations
	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name = "stockId")
	// the column which will be used as a key for map ,
	// else jpa creates a new column trade_key for storing the key of the map
	@MapKey(name = "tradeId")
	private Map<Integer, Trade> trade;
	
	
	/*
	 * @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	 * 
	 * @MapKey(name = "userId") private Map<Integer,User> user;
	 */
	 

	public Stock() {
		super();
	}

}
