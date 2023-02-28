package com.edgar.transferapi.requests;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepositWithdrawRequest {
	
	
	
	
	@NotBlank(message="source account cannot be left blank")
	private String accountNumber;
	
	@NotNull(message="amount can not be null or empty")
	@Positive
	private BigDecimal amount;
	
	

}
