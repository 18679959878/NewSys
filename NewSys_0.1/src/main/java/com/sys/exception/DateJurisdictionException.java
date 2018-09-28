package com.sys.exception;

public class DateJurisdictionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public DateJurisdictionException() {
		super();
	}

	public DateJurisdictionException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
