package com.bs.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.beans.Stock;
import com.bs.beans.StockMapping;
import com.bs.beans.Trade;
import com.bs.beans.AllTradeStockUser;
import com.bs.beans.PurchasedStock;
import com.bs.beans.User;
import com.bs.constants.BrokerageConstants;
import com.bs.dao.BrokerageAllTradeStockUserDao;
import com.bs.dao.BrokeragePurchasedStockDao;
import com.bs.dao.BrokerageStockDao;
import com.bs.dao.BrokerageTradeDao;
import com.bs.dao.BrokerageUserDao;
import com.bs.exception.InsufficientStockException;
import com.bs.exception.NoTradesFoundException;
import com.bs.exception.PurchasedStockNotFoundException;
import com.bs.exception.StockNotFoundException;
import com.bs.exception.SymbolNotFoundException;
import com.bs.exception.TradeNotFoundException;
import com.bs.exception.UserNotFoundException;

@Service
public class BrokerageServiceImpl implements BrokerageService {

	@Autowired
	BrokerageTradeDao brokerageTradeDao;

	@Autowired
	BrokerageUserDao brokerageUserDao;
	
	@Autowired
	BrokerageStockDao brokerageStockDao; 
	
	@Autowired
	BrokerageAllTradeStockUserDao brokerageAllTradeStockUserDao;
	
	@Autowired
	BrokeragePurchasedStockDao brokeragePurchasedStockDao;

	@Override
	public User postUser(User user) {
		user = brokerageUserDao.save(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		User userFromDB = getUser(user.getUserId());
		if (user.getName() != null)
			userFromDB.setName(user.getName());
		if (user.getPassword() != null)
			userFromDB.setPassword(user.getPassword());
		if (user.getRoles() != null)
			userFromDB.setRoles(user.getRoles());
		if (user.getTrades() != null)
			userFromDB.setTrades(user.getTrades());
		if ((Boolean) user.isActive() != null)
			userFromDB.setActive(user.isActive());
		// We can use the same save() method to update an existing entry in our
		// database.
		user = brokerageUserDao.save(userFromDB);
		return user;
	}

	@Override
	public User getUser(int id) {
		Optional<User> user = brokerageUserDao.findById(id);
		user.orElseThrow(() -> {
			throw new UserNotFoundException();
		});
		return user.get();
	}

	@Override
	public User getUser(String emailId) {
		Optional<User> user = brokerageUserDao.findUserByEmailId(emailId);
		user.orElseThrow(() -> {
			throw new UserNotFoundException();
		});
		return user.get();
	}

	@Override
	public List<User> getAllUsers() {
		List<User> userList = (List<User>) brokerageUserDao.findAll();
		if (userList == null)
			throw new UserNotFoundException();
		return userList;
	}

	@Override
	public User deleteUser(int id) {
		User user = getUser(id);
		brokerageUserDao.deleteById(id);
		return user;
	}

	@Override
	public boolean postTrade(Trade trade, int userId,int stockId) {
		Stock stockFromDB=getStock(stockId);
		User userFromDB = getUser(userId);
		
		  PurchasedStock pstock=brokeragePurchasedStockDao.getStock(stockId,userId);
		  if(pstock==null) {
			  if(trade.getType().equals("buy"))
				  pstock = new PurchasedStock( trade.getShares(), trade.getPrice(), userFromDB, stockId);
		  }
		  else {
			  BigDecimal totalPrice=null;
			  double totalUnits=0.0;
			  
			  switch(trade.getType()) {
			  
			  case "buy":
				  totalPrice=pstock.getTotalPrice();
				  totalPrice=totalPrice.add(trade.getPrice());
				  pstock.setTotalPrice(totalPrice);
				  totalUnits= pstock.getTotalUnits();
				  totalUnits+=trade.getShares();
				  pstock.setTotalUnits(totalUnits);
				  break;
			  case "sell":
				  totalPrice=pstock.getTotalPrice();
				  totalPrice=totalPrice.subtract(trade.getPrice());
				  pstock.setTotalPrice(totalPrice);
				  totalUnits = pstock.getTotalUnits();
				  totalUnits-=trade.getShares();
				  pstock.setTotalUnits(totalUnits);
				  if(totalUnits < 0.0)
					  throw new InsufficientStockException("Not enough stocks present!");
				  else if(totalUnits == 0.0)
					  brokeragePurchasedStockDao.deleteFromQuery(pstock.getId());
				  break;
			  
			  }
			 
			}
		  
		 if(pstock != null  ) {
			 if(pstock.getTotalUnits() != 0.0)
				 brokeragePurchasedStockDao.save(pstock);
			 //Trade tradeToInsert = new Trade(trade.getType(), stockFromDB.getSymbol(), trade.getShares(), trade.getPrice(), trade.getTimestamp());
			// tradeToInsert= brokerageTradeDao.save(tradeToInsert);
			 brokerageTradeDao.postTrade(trade.getType(), userId, stockFromDB.getSymbol(), trade.getShares(), trade.getPrice(), trade.getTimestamp(), stockId);	
			 //brokerageTradeDao.updateTrade(tradeToInsert.getType(), userId, trade.getSymbol(), trade.getShares(), trade.getPrice().doubleValue(), trade.getTimestamp(), stockId, trade.getTradeId());
			 return true; 
		 }
		 else
			 return false;
		 
		}

	@Override
	public Trade getTrade(int tradeId) {
		if (brokerageTradeDao.findById(tradeId).isPresent())
			return brokerageTradeDao.findById(tradeId).get();
		else
			throw new TradeNotFoundException("Given Trade ID does not exist");
	}
	
	
	@Override
	public Integer getStockIdForTrade(int tradeId) {
		Integer i=null;
		try {
		 i=brokerageTradeDao.getStockIdForTrade(tradeId);
		}
		catch(Exception e) {
			throw new StockNotFoundException("Trade does not have stock id");
		}
		
		return i;
	}

	@Override
	public Iterable<Trade> getAllTrades() {
		return brokerageTradeDao.findAll();
	}

	@Override
	public List<Trade> getAllTradesForUser(int userId) {
		User user = null;
		try {
			user = brokerageUserDao.findById(userId).get();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			throw new UserNotFoundException("The given user id does not exist");
		}
		Map<Integer, Trade> userMap = user.getTrades();
		List<Trade> tradeList = new ArrayList<Trade>(userMap.values());
		return tradeList;
	}
	
	@Override
	public List<Trade> getAllTradesForStock(int stockId) {
		List<Trade> tradeList = brokerageTradeDao.getAllTradesForStock(stockId);
		if (tradeList == null)
			throw new NoTradesFoundException();
		return tradeList;
	}   
		
	@Override
	public List<AllTradeStockUser> getAllTradesForStockForUser(int userId,int stockId){
		List<AllTradeStockUser> allTradeStockUserList= brokerageAllTradeStockUserDao.getAllTradeForStockForUser( userId,stockId);
		return allTradeStockUserList;
	}
	
	@Override
	public Trade updateTrade(Trade trade, int userId,int stockId) {
		
		  Trade tradeFromDB = getTrade(trade.getTradeId()); 
		  if (trade.getPrice() != null)
			  tradeFromDB.setPrice(trade.getPrice());
		  if ((Double) trade.getShares()!= null)
			  tradeFromDB.setShares(trade.getShares()); 
		  if (trade.getTimestamp() !=null) 
			  tradeFromDB.setTimestamp(trade.getTimestamp()); 
		  if (trade.getType() !=null) 
			  tradeFromDB.setType(trade.getType());
		
		Optional<Stock> stockFromDB=brokerageStockDao.findById(stockId);
		stockFromDB.orElseThrow(()-> new StockNotFoundException());
		
		int i = brokerageTradeDao.updateTrade(tradeFromDB.getType(), userId, stockFromDB.get().getSymbol(), tradeFromDB.getShares(), tradeFromDB.getPrice(), tradeFromDB.getTimestamp(), stockId,tradeFromDB.getTradeId());
		//System.out.println(i);
		
		Optional<Trade> updatedTrade=brokerageTradeDao.findById(trade.getTradeId());
		updatedTrade.orElseThrow(() -> new NoTradesFoundException());
		return updatedTrade.get();
		
	}

	@Override
	public void deleteAllTrades() {
		brokerageTradeDao.deleteAllTrades();
	}

	@Override
	public List<Trade> filterStockSymbolAndTradeType(String stocksSymbol, String tradeType, String startDate,
			String endDate) throws ParseException {
		List<Trade> listTrades = (List<Trade>) brokerageTradeDao.findAll();

		List<Trade> firstFilteredList = listTrades.stream()
				.filter(trade -> trade.getSymbol().equals(stocksSymbol))
				.collect(Collectors.toList());
		
		if (firstFilteredList.isEmpty())
			throw new SymbolNotFoundException("No such symbol exists");
		
		List<Trade> secondFilteredList = firstFilteredList.stream()
				.filter(trade -> trade.getSymbol().equals(stocksSymbol) && trade.getType().equals(tradeType))
				.collect(Collectors.toList());
		
		if(secondFilteredList.isEmpty())
			throw new TradeNotFoundException("trade type does not exist");

		List<Trade> fullFilteredList = filterByDateRange(startDate, endDate, secondFilteredList);

		fullFilteredList.forEach(System.out::println);

		return fullFilteredList;
	}
	
	@Override
	public List<Trade> filterStockSymbolAndTradeTypForUser(String stocksSymbol, String tradeType, String startDate,
			String endDate, int userId) throws ParseException {
		
		List<Trade> listTrades =getAllTradesForUser(userId);
		List<Trade> firstFilteredList = listTrades.stream()
				.filter(trade -> trade.getSymbol().equals(stocksSymbol))
				.collect(Collectors.toList());
		
		if (firstFilteredList.isEmpty())
			throw new SymbolNotFoundException("No such symbol exists");
		
		List<Trade> secondFilteredList = firstFilteredList.stream()
				.filter(trade -> trade.getSymbol().equals(stocksSymbol) && trade.getType().equals(tradeType))
				.collect(Collectors.toList());
		
		if(secondFilteredList.isEmpty())
			throw new TradeNotFoundException("trade type does not exist");

		List<Trade> fullFilteredList = filterByDateRange(startDate, endDate, secondFilteredList);

		fullFilteredList.forEach(System.out::println);

		return fullFilteredList;
		
	}
	
	@Override
	public StockMapping filterMaxMinStockPrice(String stocksSymbol, String startDate, String endDate) throws ParseException {

		List<Trade> listTrades = (List<Trade>) brokerageTradeDao.findAll();

		List<Trade> filteredList = listTrades.stream().filter(trade -> trade.getSymbol().equals(stocksSymbol))
				.collect(Collectors.toList());

		if (filteredList.isEmpty())
			throw new SymbolNotFoundException("No such symbol exists");

		List<Trade> fullFilteredList = filterByDateRange(startDate, endDate, filteredList);

		if (fullFilteredList.isEmpty())
			throw new NoTradesFoundException("There are no trades in the given date range");

		// fullFilteredList.sort((t1 ,t2) -> (int)(t1.getPrice() - t2.getPrice()));
		BigDecimal highest = fullFilteredList.get(0).getPrice();
		BigDecimal lowest = fullFilteredList.get(0).getPrice();

		for (Trade trade : fullFilteredList) {

			if (highest.compareTo(trade.getPrice()) == -1 || highest.compareTo(trade.getPrice()) == 0)
				highest = trade.getPrice();
			if (lowest.compareTo(trade.getPrice()) == 1 || lowest.compareTo(trade.getPrice()) == 0)
				lowest = trade.getPrice();
		}
		StockMapping stock = new StockMapping(stocksSymbol, highest, lowest);
		fullFilteredList.forEach(System.out::println);
		return stock;
	}

	private List<Trade> filterByDateRange(String startDate, String endDate, List<Trade> filteredList)
			throws ParseException {

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

	@Override
	public Trade deleteTrade(int id) {

		Trade trade = getTrade(id);

		if (trade == null)
			return null;

		System.out.println("Deleting===");
		brokerageTradeDao.deleteById(id);
		return trade;
	}

	

	@Override
	public Stock postStock(Stock stock) {
		Stock stockFromDB=brokerageStockDao.save(stock);
		return stockFromDB;
	}

	@Override
	public Stock getStock(int stockId) {
		Optional<Stock> stock=brokerageStockDao.findById(stockId);
		stock.orElseThrow(() -> new StockNotFoundException());
		return stock.get();
	}

	@Override
	public List<Stock> getAllStocks() {
		Iterable<Stock> stockList = brokerageStockDao.findAll();
		if(stockList == null)
			throw new StockNotFoundException();
		
		return (List<Stock>) stockList;
	}
	
	@Override
	public Stock updateStock(Stock stock) {
		Stock stockFromDB=getStock(stock.getId());
		if(stock.getName()!=null)
			stockFromDB.setName(stock.getName());
		if(stock.getPpu()!= 0)
			stockFromDB.setPpu(stock.getPpu());
		if(stock.getSymbol()!=null) {
			String symbol = stock.getSymbol();
			
			Map<Integer, Trade> mapTrades = stockFromDB.getTrade();
			Iterator<Map.Entry<Integer, Trade>> itr=mapTrades.entrySet().iterator();
			while(itr.hasNext()) {
				Entry<Integer, Trade> entry = itr.next();
				Trade trade = entry.getValue();
				trade.setSymbol(symbol);
				entry.setValue(trade);
			}
			
			stockFromDB.setSymbol(symbol);
			stockFromDB.setTrade(mapTrades);
			
		}
		
		stockFromDB=brokerageStockDao.save(stockFromDB);
		return stockFromDB;
		
	}
	
	@Override
	public void deleteStock(int stockId) {
		Stock stock=getStock(stockId);
		brokerageStockDao.delete(stock);
	}

	@Override
	public PurchasedStock getPurchasedStock(int purchasedStockId) {
		
		Optional<PurchasedStock> purchasedStock = brokeragePurchasedStockDao.findById(purchasedStockId);
		purchasedStock.orElseThrow(() -> new PurchasedStockNotFoundException("Invalid Purchased Stock Id."));
		PurchasedStock purchasedStockFromDB =  purchasedStock.get();
		
		purchasedStockFromDB=refreshPurchasedStockPrice(purchasedStockFromDB);
		purchasedStockFromDB=brokeragePurchasedStockDao.save(purchasedStockFromDB);
		return purchasedStockFromDB;
	}

	@Override
	public List<PurchasedStock> getPurchasedStockForUser(int userId) {
		//List<PurchasedStock> purchasedStockList = brokeragePurchasedStockDao.getPurchasedStockForUser(userId);
		List<PurchasedStock> purchasedStockList =  brokeragePurchasedStockDao.getPurchasedStockForUser(userId);
		if(purchasedStockList == null)
			throw new PurchasedStockNotFoundException("User has no purchased stocks.");
		
		//CopyOnWriteArrayList
		int index=0;
		Iterator<PurchasedStock> itr = purchasedStockList.iterator();
		
		while (itr.hasNext()) {
			
			PurchasedStock purchasedStock=itr.next();
			purchasedStock=refreshPurchasedStockPrice(purchasedStock);
			purchasedStockList.set(index, purchasedStock);
			++index;
		}
		
		Iterable<PurchasedStock> iterablePurchasedStockList=brokeragePurchasedStockDao.saveAll(purchasedStockList);
		
		return (List<PurchasedStock>) iterablePurchasedStockList;
	}

	private PurchasedStock refreshPurchasedStockPrice(PurchasedStock purchasedStockFromDB) {
		int stockId = purchasedStockFromDB.getStockId();
		Optional<Stock> stock= brokerageStockDao.findById(stockId);
		stock.orElseThrow(() -> new StockNotFoundException("Current market value for the stock not found"));
		Stock stockFromDB = stock.get();
		double totalPrice = purchasedStockFromDB.getTotalUnits() * stockFromDB.getPpu();
		purchasedStockFromDB.setTotalPrice(BigDecimal.valueOf(totalPrice));
		return purchasedStockFromDB;
	}

	
	

}
