package com.edgar.transferapi.repsonse;

public class MessageResponse {
	
	private String error;
	
	
	public MessageResponse() {
		
	}


	public MessageResponse(String error) {
		super();
		this.error = error;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}
	
	

}
