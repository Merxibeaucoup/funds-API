package com.edgar.transferapi.requests;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransferRequest {
	
	
	@NotBlank(message="source account cannot be left blank")
	private String from_account_number;
	
	@NotBlank(message="destination account cannot be left blank")
	private String destination_account_number;
	
	@NotNull(message="amount can not be null or empty")
	@Positive(message="amount must be greater than 0.00")
	private BigDecimal amount_to_send;
	
	
	


}
