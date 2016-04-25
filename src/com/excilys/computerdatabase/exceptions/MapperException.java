package com.excilys.computerdatabase.exceptions;

public class MapperException extends Exception {
	private String message;
	
	public MapperException(String message){
		super(message);
	}
}
