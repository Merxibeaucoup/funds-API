package com.edgar.transferapi.models;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Account {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(nullable=false, unique = true)
	@NotBlank
	private String account_number;
	
	@NotNull(message="balance can not be empty")
	private BigDecimal balance;
	
	@ManyToOne
	private User user; // many accounts can belong to one user
	
	public Account() {
		
	}

	public Account( @NotBlank String account_number,
			@NotNull(message = "balance can not be empty") BigDecimal balance, User user) {
		super();
		this.account_number = account_number;
		this.balance = balance;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", account_number=" + account_number + ", balance=" + balance + ", user=" + user
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(account_number, balance, id, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(account_number, other.account_number) && Objects.equals(balance, other.balance)
				&& Objects.equals(id, other.id) && Objects.equals(user, other.user);
	}
	
	
	

}
