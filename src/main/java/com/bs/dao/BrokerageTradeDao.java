package com.bs.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bs.beans.Trade;

public interface BrokerageTradeDao extends CrudRepository<Trade, Integer> {
	@Modifying
	@Transactional
	@Query(value="DELETE FROM Trade")
	void deleteAllTrades();
}

