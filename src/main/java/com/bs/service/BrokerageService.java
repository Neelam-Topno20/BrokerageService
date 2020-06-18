package com.bs.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.bs.beans.Stock;
import com.bs.beans.Trade;
import com.bs.beans.User;

public interface BrokerageService {

	Trade postTrade(Trade trade);

	User postUser(User user);

	void deleteAllTrades();

	Optional<Trade> getTrade(int tradeId);

	Iterable<Trade> getAllTrades();

	List<Trade> getAllTradesForUser(int userId);

	List<Trade> filterStockSymbolAndTradeType(String stocksSymbol, String tradeType, String startDate, String endDate)
			throws ParseException;
	
	Stock filterMaxMinStockPrice(String stocksSymbol,String startDate, String endDate) throws ParseException;
}
