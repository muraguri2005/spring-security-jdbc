package org.freelesson.springsecurityjdbc.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 346138023644327330L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}


	public HttpStatus getExceptionStatus() {
		return HttpStatus.BAD_REQUEST;
	}

}