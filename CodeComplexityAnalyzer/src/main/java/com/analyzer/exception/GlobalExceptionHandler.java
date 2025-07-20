package com.analyzer.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(ASTConversionException.class)
	    public ResponseEntity<Map<String, String>> handleASTConversionException(ASTConversionException ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "AST Conversion Failed");
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    }
	 
	 @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Internal Server Error");
	        errorResponse.put("message", ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}
