package com.bs.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bs.beans.AuthenticationRequest;
import com.bs.beans.AuthenticationResponse;
import com.bs.beans.Stock;
import com.bs.beans.Trade;
import com.bs.beans.User;
import com.bs.security.JwtUtil;
import com.bs.security.MyUserDetailsService;
import com.bs.service.BrokerageService;

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
	
	@PostMapping(value="authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
				);
		}
		catch(BadCredentialsException e) {
			throw new BadCredentialsException("Invalid credentials");
		}
		
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
 
	@PostMapping(value = "trades")
	public ResponseEntity<String> postTrades(@Valid @RequestBody Trade trade) {
		brokerageService.postTrade(trade);
		return new ResponseEntity<>("Trades inserted", HttpStatus.CREATED);
	}

	@PostMapping(value = "users")
	public ResponseEntity<String> postUser(@RequestBody User user) {
		brokerageService.postUser(user);
		return new ResponseEntity<>("User inserted", HttpStatus.CREATED);
	}

	@DeleteMapping(value = "erase")
	public ResponseEntity<String> deleteAllTrades() {
		brokerageService.deleteAllTrades();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "trades/{id}")
	public ResponseEntity<Optional<Trade>> getTrade(@PathVariable("id") int tradeId) {
		Optional<Trade> trade = brokerageService.getTrade(tradeId);
		return new ResponseEntity<>(trade, HttpStatus.OK);

	}

	@GetMapping("trades")
	public ResponseEntity<Iterable<Trade>> getAllTrades() {
		Iterable<Trade> tradeList = brokerageService.getAllTrades();
		return new ResponseEntity<Iterable<Trade>>(tradeList, HttpStatus.OK);
	}

	@GetMapping("trades/users/{userId}")
	public ResponseEntity<List<Trade>> getAllTradesForUser(@PathVariable("userId") int userId) {
		List<Trade> tradeList = brokerageService.getAllTradesForUser(userId);
		return new ResponseEntity<List<Trade>>(tradeList, HttpStatus.OK);
	}

	@GetMapping("trades/stocks/{stockssymbol}")
	public ResponseEntity<List<Trade>> filterStockSymbolAndTradeType(@PathVariable("stockssymbol") String stocksSymbol,
			@RequestParam("type") String tradeType, @RequestParam("start") String startDate,
			@RequestParam("end") String endDate) throws ParseException {
		List<Trade> filteredList = brokerageService.filterStockSymbolAndTradeType(stocksSymbol, tradeType, startDate,
				endDate);
		return new ResponseEntity<List<Trade>>(filteredList, HttpStatus.OK);
	}

	@GetMapping("stocks/{stocksymbol}/price")
	public ResponseEntity<Stock> filterMaxMinStockPrice(@PathVariable("stocksymbol") String stockSymbol,
			@RequestParam("start") String startDate, @RequestParam("end") String endDate) throws ParseException {
		Stock stock = brokerageService.filterMaxMinStockPrice(stockSymbol, startDate, endDate);
		return new ResponseEntity<Stock>(stock, HttpStatus.OK);
	}

	@GetMapping("health")
	public String healthCheck() {
		return "Application Running";
	}

}
