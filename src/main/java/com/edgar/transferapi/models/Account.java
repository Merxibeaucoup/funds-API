package com.edgar.transferapi.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.edgar.transferapi.models.user.User;

@Entity
public class Account {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(nullable=false, unique = true)
	@NotBlank
	private String number;
	
	@NotNull(message="balance can not be empty")
	private BigDecimal balance;
	
	/* many created accounts belongs to a user **/
	@ManyToOne private User user;
	
	public Account() {
		
	}

	public Account(Long id, @NotBlank String number, @NotNull(message = "balance can not be empty") BigDecimal balance,
			User user) {
		super();
		this.id = id;
		this.number = number;
		this.balance = balance;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", number=" + number + ", balance=" + balance + ", user=" + user + "]";
	}

	
	
	

}
