package com.common;

public class DefineServiceException extends RuntimeException{

	private String message;
	private String code;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DefineServiceException(String message) {
		super(message);
		this.message = message;
	}
	
	public DefineServiceException(String message,Throwable e) {
		super(message,e);
		this.message = message;
	}
	
	public DefineServiceException(String message,String code) {
		super(message);
		this.message = message;
		this.code = code;
	}
	
	public DefineServiceException(String message,String code,Throwable e) {
		super(message,e);
		this.message = message;
		this.code = code;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	
	

}
