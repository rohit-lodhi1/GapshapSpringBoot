package com.gapshap.app.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> userNotFoundException(UserNotFoundException ex){
		Map<String, Object> response = new HashMap<>();
		response.put(AppConstants.MESSAGE, ex.getMessage());
		response.put(AppConstants.STATUS_CODE,AppConstants.STATUS_CODE_404);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
}
