package com.bs.aspect;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bs.customresponse.ErrorResponse;
import com.bs.customresponse.MessageResponse;
import com.bs.exception.NoTradesFoundException;
import com.bs.exception.SymbolNotFoundException;
import com.bs.exception.TradeNotFoundException;
import com.bs.exception.UserNotFoundException;

@ControllerAdvice(basePackages = {"com.bs.controller"})
public class BrokerageServiceAspect {
	
	@ExceptionHandler(ConstraintViolationException.class )
	public ResponseEntity<ErrorResponse> handleJdbcSQLIntegrityConstraintViolationException(Exception e) {
		ErrorResponse customResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(TradeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTradeNotFoundException(RuntimeException re){
		ErrorResponse customResponse=new ErrorResponse(HttpStatus.NOT_FOUND.value(), re.getMessage());
		return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(RuntimeException re){
		ErrorResponse customResponse= new ErrorResponse(HttpStatus.NOT_FOUND.value(),re.getMessage() );
		return new ResponseEntity<ErrorResponse>(customResponse,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(SymbolNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSymbolNotFoundException(RuntimeException re){
		ErrorResponse customResponse= new ErrorResponse(HttpStatus.NOT_FOUND.value(),re.getMessage() );
		return new ResponseEntity<ErrorResponse>(customResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoTradesFoundException.class)
	public ResponseEntity<MessageResponse> handleNoTradesFoundException(RuntimeException re){
		MessageResponse customResponse = new MessageResponse(re.getMessage());
		return new ResponseEntity<MessageResponse>(customResponse, HttpStatus.OK);
		
	}
}
