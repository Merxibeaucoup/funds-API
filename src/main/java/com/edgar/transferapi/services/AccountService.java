package com.edgar.transferapi.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.transferapi.models.Account;
import com.edgar.transferapi.models.user.User;
import com.edgar.transferapi.repositories.AccountRepository;
import com.edgar.transferapi.requests.TransferRequest;

@Service
public class AccountService {
	
	
	@Autowired
	private AccountRepository accountRepo;
	
	
	/* checks if there is balance money for you to send funds **/
	public boolean not_have_enough_balance(TransferRequest transferRequest, Account source_account) {
        return source_account.getBalance().compareTo(transferRequest.getAmount_to_send()) != 1;
    }
	
	
	
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
		
		
		else throw new RuntimeException();
			
		
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
