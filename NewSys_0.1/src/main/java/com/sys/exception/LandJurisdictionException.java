package com.sys.exception;

import java.io.IOException;

public class LandJurisdictionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// “Ï≥£–≈œ¢
	public String message;

	public LandJurisdictionException(String message) {
		super(message);
		this.message = message;
	}
	public LandJurisdictionException(){
		super();
		System.out.println("¥ÌŒÛ");
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
