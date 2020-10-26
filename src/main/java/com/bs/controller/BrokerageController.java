package com.bs.controller;

import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bs.beans.AllTradeStockUser;
import com.bs.beans.AuthenticationRequest;
import com.bs.beans.AuthenticationResponse;
import com.bs.beans.PurchasedStock;
import com.bs.beans.Stock;
import com.bs.beans.StockMapping;
import com.bs.beans.Trade;
import com.bs.beans.User;
import com.bs.security.JwtUtil;
import com.bs.security.MyUserDetailsService;
import com.bs.service.BrokerageService;

//Allow all origins/domain
@CrossOrigin(origins = "*")
@RestController
public class BrokerageController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private BrokerageService brokerageService;

	// Basic Authentication
	@RequestMapping(value = "/basicAuth", method = RequestMethod.GET)
	public ResponseEntity<Integer> test(@RequestHeader("authorization") String basicAuth) {

		basicAuth = basicAuth.substring(6);
		byte[] decodedBytes = Base64.getDecoder().decode(basicAuth);
		String decodedString = new String(decodedBytes);
		String[] credentials = decodedString.split(":");
		User user = brokerageService.getUser(credentials[0]);
		return new ResponseEntity<>(user.getUserId(), HttpStatus.OK);
	}

	// Jwt Authentication
	@PostMapping(value = "authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid credentials");
		}

		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtUtil.generateToken(userDetails);

		User user = brokerageService.getUser(authenticationRequest.getUsername());

		String userId = String.valueOf(user.getUserId());

		String role = user.getRoles();

		return ResponseEntity.ok(new AuthenticationResponse(jwt, userId, role));
	}

	@PostMapping(value = "users")
	public ResponseEntity<User> postUser(@RequestBody User user) {
		user = brokerageService.postUser(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@GetMapping(value = "users")
	public ResponseEntity<User> getUser(@RequestParam int id) {
		User user = brokerageService.getUser(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping(value = "users/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = brokerageService.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@PutMapping(value = "users")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		user = brokerageService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// admin
	@DeleteMapping(value = "users")
	public ResponseEntity<User> deleteUser(@RequestParam int id) {
		User user = brokerageService.deleteUser(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping(value = "trades/user/{userId}/stock/{stockId}")
	public ResponseEntity<HttpStatus> postTrades(@Valid @RequestBody Trade trade, @PathVariable("userId") int userId,
			@PathVariable("stockId") int stockId) {
		boolean flag=brokerageService.postTrade(trade, userId, stockId);
		// System.out.println(tradeAfterInsertion);
		if(flag)
			return new ResponseEntity<>(HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}

	// need to separate to user basis and admin basis -- handled in ui
	@GetMapping(value = "trades/{id}")
	public ResponseEntity<Trade> getTrade(@PathVariable("id") int tradeId) {
		Trade trade = brokerageService.getTrade(tradeId);
		return new ResponseEntity<>(trade, HttpStatus.OK);

	}
	
	@GetMapping(value="trade/stock/{tradeId}")
	public ResponseEntity<Integer> getStockIdForTrade(@PathVariable("tradeId")int tradeId){
		Integer i=brokerageService.getStockIdForTrade(tradeId);
		return new ResponseEntity<>(i,HttpStatus.OK);
	}
	
	

	// admin
	@GetMapping("trades")
	public ResponseEntity<Iterable<Trade>> getAllTrades() {
		Iterable<Trade> tradeList = brokerageService.getAllTrades();
		return new ResponseEntity<Iterable<Trade>>(tradeList, HttpStatus.OK);
	}

	// need to separate to user basis and admin basis -- handled in ui
	@GetMapping("trades/users/{userId}")
	public ResponseEntity<List<Trade>> getAllTradesForUser(@PathVariable("userId") int userId) {
		List<Trade> tradeList = brokerageService.getAllTradesForUser(userId);
		return new ResponseEntity<List<Trade>>(tradeList, HttpStatus.OK);
	}

	@GetMapping("trades/stock/{stockId}")
	public ResponseEntity<List<Trade>> getAllTradesForStock(@PathVariable("stockId") int stockId) {
		return new ResponseEntity<List<Trade>>(brokerageService.getAllTradesForStock(stockId), HttpStatus.OK);
	}
	
	@GetMapping("trades/user/{userId}/stock/{stockId}")
	public ResponseEntity<List<AllTradeStockUser>> getAllTradesForStockForUser(@PathVariable("userId")int userId,@PathVariable("stockId")int stockId) {
		List<AllTradeStockUser> entity=brokerageService.getAllTradesForStockForUser(userId, stockId);
		return new ResponseEntity<List<AllTradeStockUser>>(entity,HttpStatus.OK);
	}

	// need to separate to user basis and admin basis -- handled in ui
	@PutMapping(value = "trades/user/{userId}/stock/{stockId}")
	public ResponseEntity<Trade> updateTrades(@RequestBody @Valid Trade trade, @PathVariable("userId") int userId,
			@PathVariable("stockId") int stockId) {
		trade = brokerageService.updateTrade(trade, userId, stockId);
		return new ResponseEntity<Trade>(trade, HttpStatus.OK);
	}

	// need to separate to user basis and admin basis -- handled in ui
	@DeleteMapping(value = "trades/{id}")
	public ResponseEntity<String> deleteTrade(@PathVariable("id") int id) {
		Trade trade = brokerageService.deleteTrade(id);
		if (trade == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		// System.out.println(trade);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// admin
	@DeleteMapping(value = "erase")
	public ResponseEntity<String> deleteAllTrades() {
		brokerageService.deleteAllTrades();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("trades/stocks/{stockssymbol}")
	public ResponseEntity<List<Trade>> filterStockSymbolAndTradeType(@PathVariable("stockssymbol") String stocksSymbol,
			@RequestParam("type") String tradeType, @RequestParam("start") String startDate,
			@RequestParam("end") String endDate) throws ParseException {
		List<Trade> filteredList = brokerageService.filterStockSymbolAndTradeType(stocksSymbol, tradeType, startDate,
				endDate);
		return new ResponseEntity<List<Trade>>(filteredList, HttpStatus.OK);
	}
	
	@GetMapping("trades/user/{userId}/stocks/{stockssymbol}")
	public ResponseEntity<List<Trade>> filterStockSymbolAndTradeTypeForUser(@PathVariable("stockssymbol") String stocksSymbol,
			@RequestParam("type") String tradeType, @RequestParam("start") String startDate,
			@RequestParam("end") String endDate, @PathVariable("userId")int userId) throws ParseException {
		List<Trade> filteredList = brokerageService.filterStockSymbolAndTradeTypForUser(stocksSymbol, tradeType, startDate, endDate, userId);
		return new ResponseEntity<List<Trade>>(filteredList, HttpStatus.OK);
	}

	@GetMapping("stocks/{stocksymbol}/price")
	public ResponseEntity<StockMapping> filterMaxMinStockPrice(@PathVariable("stocksymbol") String stockSymbol,
			@RequestParam("start") String startDate, @RequestParam("end") String endDate) throws ParseException {
		StockMapping stock = brokerageService.filterMaxMinStockPrice(stockSymbol, startDate, endDate);
		return new ResponseEntity<StockMapping>(stock, HttpStatus.OK);
	}

	@PostMapping("stocks")
	public ResponseEntity<Stock> postStock(@RequestBody Stock stock) {
		stock = brokerageService.postStock(stock);
		return new ResponseEntity<Stock>(stock, HttpStatus.CREATED);
	}
	
	@GetMapping("stocks/{stockId}")
	public ResponseEntity<Stock> getStock(@PathVariable("stockId")int stockId){
		Stock stock=brokerageService.getStock(stockId);
		return new ResponseEntity<Stock>(stock,HttpStatus.OK);
	}
	
	@GetMapping("stocks/all")
	public ResponseEntity<List<Stock>> getAllStock(){
		List<Stock> stockList = brokerageService.getAllStocks();
		return new ResponseEntity<List<Stock>>(stockList,HttpStatus.OK);
	}
	
	@PutMapping("stocks")
	public ResponseEntity<Stock> updateStock(@RequestBody Stock stock){
		stock=brokerageService.updateStock(stock);
		return new ResponseEntity<Stock>(stock,HttpStatus.OK);
		
	}
	
	@DeleteMapping("stocks/{stockId}")
	public ResponseEntity<HttpStatus> deleteStock(@PathVariable("stockId") int stockId){
		brokerageService.deleteStock(stockId);
		return  new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@GetMapping("users/purchasedStocks/{purchasedStockId}")
	public ResponseEntity<PurchasedStock> getPurchasedStock(@PathVariable("purchasedStockId")int purchasedStockId){
		PurchasedStock purchasedStock = brokerageService.getPurchasedStock(purchasedStockId);
		return new ResponseEntity<PurchasedStock>(purchasedStock,HttpStatus.OK);
	}
	
	@GetMapping("users/{userId}/purchasedStocks")
	public ResponseEntity<List<PurchasedStock>> getPurchasedStockForUser(@PathVariable("userId")int userId){
		List<PurchasedStock> purchasedStockList = brokerageService.getPurchasedStockForUser(userId);
		return new ResponseEntity<List<PurchasedStock>>(purchasedStockList,HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("health")
	public HashMap<String, String> healthCheck() {

		HashMap<String, String> map = new HashMap<>();
		map.put("status", "Application Running");
		return map;
	}

}
