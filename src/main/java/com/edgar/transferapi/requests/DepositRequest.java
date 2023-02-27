package com.edgar.transferapi.requests;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepositRequest {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="source account cannot be left blank")
	private String accountNumber;
	
	@NotNull(message="amount can not be null or empty")
	@Size(min=1, message="value must be greater than zero")
	private BigDecimal amountToDeposit;

}
