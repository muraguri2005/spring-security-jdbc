package org.freelesson.springsecurityjdbc.exception;

import java.util.Map;

public final class ErrorResponse  {

	private int status;
	private Map<String, Map<String, String>> errors;
	private String message;

	public ErrorResponse(int status, String message) {
		this.setStatus(status);
		this.setMessage(message);
	}

	
	public int getStatus() {
		return status;
	}

	void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Map<String, String>> getErrors() {
		return errors;
	}

}
