package com.edgar.transferapi.requests;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.edgar.transferapi.models.User;

public class TransferRequest {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="source account cannot be left blank")
	private String from_account_number;
	
	@NotBlank(message="destination account cannot be left blank")
	private String destination_account_number;
	
	@NotNull(message="amount can not be null or empty")
	@Size(min=1, message="value must be greater than zero")
	private BigDecimal amount_to_send;
	
	@OneToOne
	private User user_transfer; // one User(has) needed to do one transfer
	
	public TransferRequest() {
		
	}

	public TransferRequest( @NotBlank(message = "source account cannot be left blank") String from_account_number,
			@NotBlank(message = "destination account cannot be left blank") String destination_account_number,
			@NotNull(message = "amount can not be null or empty") @Size(min = 1, message = "value must be greater than zero") BigDecimal amount_to_send,
			User user_transfer) {
		super();
		this.from_account_number = from_account_number;
		this.destination_account_number = destination_account_number;
		this.amount_to_send = amount_to_send;
		this.user_transfer = user_transfer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom_account_number() {
		return from_account_number;
	}

	public void setFrom_account_number(String from_account_number) {
		this.from_account_number = from_account_number;
	}

	public String getDestination_account_number() {
		return destination_account_number;
	}

	public void setDestination_account_number(String destination_account_number) {
		this.destination_account_number = destination_account_number;
	}

	public BigDecimal getAmount_to_send() {
		return amount_to_send;
	}

	public void setAmount_to_send(BigDecimal amount_to_send) {
		this.amount_to_send = amount_to_send;
	}

	public User getUser_transfer() {
		return user_transfer;
	}

	public void setUser_transfer(User user_transfer) {
		this.user_transfer = user_transfer;
	}

	@Override
	public String toString() {
		return "Transfer [id=" + id + ", from_account_number=" + from_account_number + ", destination_account_number="
				+ destination_account_number + ", amount_to_send=" + amount_to_send + ", user_transfer=" + user_transfer
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount_to_send, destination_account_number, from_account_number, id, user_transfer);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferRequest other = (TransferRequest) obj;
		return Objects.equals(amount_to_send, other.amount_to_send)
				&& Objects.equals(destination_account_number, other.destination_account_number)
				&& Objects.equals(from_account_number, other.from_account_number) && Objects.equals(id, other.id)
				&& Objects.equals(user_transfer, other.user_transfer);
	}
	
	


}
