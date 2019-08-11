package org.freelesson.springsecurityjdbc.exception;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 346138023644327330L;

	public ObjectNotFoundException() {
		super();
	}

	public ObjectNotFoundException(String message) {
		super(message);
	}

	public HttpStatus getExceptionStatus() {
		return HttpStatus.NOT_FOUND;
	}
	
	

}
