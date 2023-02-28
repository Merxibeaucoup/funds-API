package com.edgar.transferapi.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.transferapi.exceptions.AccountDoesntExistException;
import com.edgar.transferapi.exceptions.AccountNumberAlreadyExists;
import com.edgar.transferapi.exceptions.InsufficentBalanceException;
import com.edgar.transferapi.models.Account;
import com.edgar.transferapi.models.user.User;
import com.edgar.transferapi.repositories.AccountRepository;
import com.edgar.transferapi.requests.DepositWithdrawRequest;

@Service
public class AccountService {
	
	
	@Autowired
	private AccountRepository accountRepo;
	
	
	
	/* register account **/
	public Account newAccount(Account account, User user) {
		
		
		boolean accountNumberTaken = true ;		
		String temp = "";
		
		
		while (accountNumberTaken) {
			temp = accountNumGeneration();
			
			if(!accountRepo.findByNumber(temp).isPresent())
				accountNumberTaken = false;
		}
		
		
		BigDecimal initialValue = new BigDecimal("0.00");
		
		if(!isExists(account.getNumber())) {
			account.setNumber(temp);
			account.setBalance(initialValue);
			account.setUser(user);
			return accountRepo.save(account);
		}
		
		
		else throw new AccountNumberAlreadyExists("An account with this number already exists, "
				+ "please wait a few seconds and try again");		
		
	}
	
	
	/*  getAccount By number **/
	public Account getAccountByNumber(String accountNumber) {		
		Optional<Account> account = accountRepo.findByNumber(accountNumber);		 
		return account.orElseThrow( ()-> new AccountDoesntExistException("Account number doesnt Exist for : " + accountNumber));
	}
	
	
	
	/*  Deposit funds into account **/
	public void makeDeposit(DepositWithdrawRequest depositRequest, Account account ) {
				
		if(isExists(account.getNumber())) {		
			account.setBalance(account.getBalance().add(depositRequest.getAmount()));			
			 accountRepo.save(account) ;
			
		}
		
		else throw new AccountDoesntExistException("Account number doesnt Exist");				
	}
	
	
	
	/* 
	 *  Withdraw funds from account 
	 * Maybe i should REFACTOR, have just one method for deposit
	 *  and withdrawal then use ENUMs to distinguish actions  ??
	 *  
	 *  we will see!!
	 **/
	public void makeWithdrawal(DepositWithdrawRequest WithdrawRequest, Account account ) {
				
		if(isExists(account.getNumber())) {	
			if(WithdrawRequest.getAmount().compareTo(account.getBalance())==1) throw new InsufficentBalanceException("Unable to withdraw money, insufficient funds in your account !");
			
			
			account.setBalance(account.getBalance().subtract(WithdrawRequest.getAmount()));			
			 accountRepo.save(account) ;
			
		}
		
		else throw new AccountDoesntExistException("Account number doesnt Exist");				
	}
	
	
	
	/** utils */
	
	/* generate account number **/
	private String accountNumGeneration() {
		String companyABV = "FNY";
		
		long generatedNumber = (long)Math.floor(Math.random() * 1_000_000_000);
		
		return companyABV + generatedNumber;
	}
	

	/* check if exists by account number **/
	private boolean isExists(String accountNum) {
		if(accountRepo.existsByNumber(accountNum)) {
			return true;
		}
		else return false;
	}
}
