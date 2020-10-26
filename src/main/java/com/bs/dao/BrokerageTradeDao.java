package com.bs.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bs.beans.Trade;
import com.bs.beans.AllTradeStockUser;

import static com.bs.constants.QueryStrings.INSERT_TRADE_QUERY;
import static com.bs.constants.QueryStrings.UPDATE_TRADE_QUERY;
import static com.bs.constants.QueryStrings.SELECT_ALL_TRADES_WHERE_STOCK_QUERY;
import static com.bs.constants.QueryStrings.SELECT_STOCK_ID_FROM_TRADE_QUERY;

public interface BrokerageTradeDao extends CrudRepository<Trade, Integer> {
	@Modifying
	@Transactional
	@Query(value="DELETE FROM Trade")
	void deleteAllTrades();
	
	//Modifying used for insert or update or delete operations 
	//Modifying returns int or void as data type
	@Modifying
	//Transactional Required for executing insert/update/delete operations
	@Transactional
	@Query(value=INSERT_TRADE_QUERY , nativeQuery = true)
	int postTrade(String type,int userId,String symbol,double shares,BigDecimal price,String timestamp,int stockId);
	
	//@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Modifying
	@Transactional
	@Query(value= UPDATE_TRADE_QUERY , nativeQuery = true)
	//@Query(value= UPDATE_TRADE_QUERY_HIBERNATE,nativeQuery = false )
	int updateTrade(String type,int userId,String symbol,double shares,BigDecimal price,String timestamp,int stockId,int tradeId);
	//int updateTrade(String type, int userId, String symbol, double shares, double price, String timestamp,int stockId, int tradeId);
	//int updateTrade(@Param("type")String type, @Param("userId")int userId,@Param("symbol") String symbol, @Param("shares")double shares,@Param("price") double price,@Param("timestamp") String timestamp,@Param("stockId")int stockId, @Param("tradeId")int tradeId);
	
	
	@Query(value = SELECT_ALL_TRADES_WHERE_STOCK_QUERY,nativeQuery = true)
	//For custom result set create a new dao with the custom result set type
	//List<AllTradeStockUser> getAllTradesForStock(int stockId);
	List<Trade> getAllTradesForStock(int stockId);
	
	@Query(value=SELECT_STOCK_ID_FROM_TRADE_QUERY,nativeQuery = true)
	int getStockIdForTrade(int tradeId);

	
}

