package com.edgar.transferapi.requests;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class FindByNumberRequest {
	
	
	@NotBlank(message ="account number can not be blank")
	private String account_number;
	
	private BigDecimal balance;

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	

}
