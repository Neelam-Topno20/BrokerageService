package com.bs.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bs.beans.StockMapping;
import com.bs.beans.Trade;
import com.bs.beans.AllTradeStockUser;
import com.bs.beans.PurchasedStock;
import com.bs.beans.Stock;
import com.bs.beans.User;

public interface BrokerageService {

	User postUser(User user);
	
	User updateUser(User user);
	
	User getUser(int id);
	
	User getUser(String emailId);
	
	List<User> getAllUsers();
	
	User deleteUser(int id);
	
	boolean postTrade(Trade trade,int userId,int stockId);
	
	Trade getTrade(int tradeId);

	Iterable<Trade> getAllTrades();

	List<Trade> getAllTradesForUser(int userId);
	
	List<Trade> getAllTradesForStock(int stockId);
	
	Trade updateTrade(Trade trade, int userId, int stockId);
	
	void deleteAllTrades();
	
	Trade deleteTrade(int id);

	List<Trade> filterStockSymbolAndTradeType(String stocksSymbol, String tradeType, String startDate, String endDate)
			throws ParseException;
	
	List<Trade> filterStockSymbolAndTradeTypForUser(String stocksSymbol, String tradeType, String startDate, String endDate,int userId)
			throws ParseException;
	
	StockMapping filterMaxMinStockPrice(String stocksSymbol,String startDate, String endDate) throws ParseException;

	Stock postStock(Stock stock);

	Stock getStock(int stockId);

	List<Stock> getAllStocks();

	void deleteStock(int stockId);

	Stock updateStock(Stock stock);

	List<AllTradeStockUser> getAllTradesForStockForUser(int userId, int stockId);

	PurchasedStock getPurchasedStock(int purchasedStockId);

	List<PurchasedStock> getPurchasedStockForUser(int userId);
	
	Integer getStockIdForTrade(int tradeId);


}
