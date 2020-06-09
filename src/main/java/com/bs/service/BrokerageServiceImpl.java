package com.bs.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.beans.Stock;
import com.bs.beans.Trade;
import com.bs.beans.User;
import com.bs.constants.BrokerageConstants;
import com.bs.dao.BrokerageTradeDao;
import com.bs.dao.BrokerageUserDao;
import com.bs.exception.NoTradesFoundException;
import com.bs.exception.SymbolNotFoundException;
import com.bs.exception.TradeNotFoundException;
import com.bs.exception.UserNotFoundException;

@Service
public class BrokerageServiceImpl implements BrokerageService {

	@Autowired
	BrokerageTradeDao brokerageTradeDao;

	@Autowired
	BrokerageUserDao brokerageUserDao;

	@Override
	public Trade postTrade(Trade trade) {
		System.out.print(trade);
		trade = brokerageTradeDao.save(trade);
		return trade;
	}

	@Override
	public User postUser(User user) {
		System.out.print(user);
		user = brokerageUserDao.save(user);
		return user;
	}

	@Override
	public void deleteAllTrades() {
		brokerageTradeDao.deleteAllTrades();
	}

	@Override
	public Optional<Trade> getTrade(int tradeId) {
		if(brokerageTradeDao.findById(tradeId).isPresent())
			return brokerageTradeDao.findById(tradeId);
		else
			throw new TradeNotFoundException("Given Trade ID does not exist");
	}

	@Override
	public Iterable<Trade> getAllTrades() {
		return brokerageTradeDao.findAll();
	}

	@Override
	public List<Trade> getAllTradesForUser(int userId) {
		User user=null;
		try {
		 user = brokerageUserDao.findById(userId).get();
		}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			throw new UserNotFoundException("The given user id does not exist");
		}
		Map<Integer, Trade> userMap = user.getTrades();
		List<Trade> tradeList = new ArrayList<Trade>(userMap.values());
		return tradeList;
	}

	@Override
	public List<Trade> filterStockSymbolAndTradeType(String stocksSymbol, String tradeType, String startDate,
			String endDate) throws ParseException {
		List<Trade> listTrades = (List<Trade>) brokerageTradeDao.findAll();

		List<Trade> filteredList = listTrades.stream()
				.filter(trade -> trade.getSymbol().equals(stocksSymbol) && trade.getType().equals(tradeType))
				.collect(Collectors.toList());
		
		if(filteredList.isEmpty())
			throw new SymbolNotFoundException("No such symbol exists");

		List<Trade> fullFilteredList = filterByDateRange(startDate, endDate, filteredList);

		fullFilteredList.forEach(System.out::println);

		return fullFilteredList;
	}

	@Override
	public Stock filterMaxMinStockPrice(String stocksSymbol, String startDate, String endDate) throws ParseException {
		
		List<Trade> listTrades = (List<Trade>) brokerageTradeDao.findAll();

		List<Trade> filteredList = listTrades.stream()
				.filter(trade -> trade.getSymbol().equals(stocksSymbol))
				.collect(Collectors.toList());
		
		if(filteredList.isEmpty())
			throw new SymbolNotFoundException("No such symbol exists");

		List<Trade> fullFilteredList = filterByDateRange(startDate, endDate, filteredList);
		
		if(fullFilteredList.isEmpty())
			throw new NoTradesFoundException("There are no trades in the given date range");
		
		//fullFilteredList.sort((t1 ,t2) -> (int)(t1.getPrice() - t2.getPrice()));
		BigDecimal highest = fullFilteredList.get(0).getPrice();
		BigDecimal lowest = fullFilteredList.get(0).getPrice();
		
		for (Trade trade : fullFilteredList) {
			
			if(highest.compareTo(trade.getPrice()) == -1 || highest.compareTo(trade.getPrice())==0)
				highest=trade.getPrice();
			if(lowest.compareTo(trade.getPrice()) == 1 || lowest.compareTo(trade.getPrice()) == 0 )
				lowest = trade.getPrice();
		}
		 Stock stock =new Stock(stocksSymbol, highest, lowest);
		fullFilteredList.forEach(System.out::println);
		return stock;
	}

	private List<Trade> filterByDateRange(String startDate, String endDate, List<Trade> filteredList) throws ParseException {

		String pattern = BrokerageConstants.TIMESTAMP_FORMAT;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date1 = sdf.parse(startDate);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Date date3 = sdf.parse(endDate);
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(date3);

		List<Trade> dateFilteredList = filteredList.stream().filter(trade -> {
			Date date2 = null;
			try {
				date2 = sdf.parse(trade.getTimestamp());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date2);
			return cal1.before(cal2) && cal3.after(cal2);
		}).collect(Collectors.toList());

		dateFilteredList.forEach(System.out::println);

		return dateFilteredList;

	}

}
