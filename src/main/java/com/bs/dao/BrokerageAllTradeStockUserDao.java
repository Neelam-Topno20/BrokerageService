package com.bs.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bs.beans.AllTradeStockUser;

import static com.bs.constants.QueryStrings.SELECT_ALL_TRADES_WHERE_STOCK_AND_USER_QUERY;

import java.util.List;

public interface BrokerageAllTradeStockUserDao extends CrudRepository<AllTradeStockUser, Integer> {
	
	@Query(value = SELECT_ALL_TRADES_WHERE_STOCK_AND_USER_QUERY , nativeQuery = true)
	public List<AllTradeStockUser> getAllTradeForStockForUser(int userId, int stockId);

}
