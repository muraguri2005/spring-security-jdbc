package org.freelesson.springsecurityjdbc.domain;

public class Welcome {
	private String greetings;
	public Welcome(String greetings) {
		this.greetings = greetings;

	}
	public String getGreetings() {
		return greetings;
	}
	public void setGreetings(String greetings) {
		this.greetings = greetings;
	}
	
	Welcome() {
		
	}

}
