package com.analyzer.exception;

public class ASTConversionException extends RuntimeException {

	public ASTConversionException(String message) {
		super(message);
		
	}
	
	public ASTConversionException(String message, Throwable cause) {
		super(message,cause);
	}
}
