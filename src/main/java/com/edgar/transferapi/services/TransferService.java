package com.edgar.transferapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.transferapi.exceptions.AccountDoesntExistException;
import com.edgar.transferapi.models.Account;
import com.edgar.transferapi.models.Transfer;
import com.edgar.transferapi.models.user.User;
import com.edgar.transferapi.repositories.AccountRepository;
import com.edgar.transferapi.requests.TransferRequest;

@Service
public class TransferService {
	
	
	@Autowired
	private AccountRepository accountRepo;

	public Transfer transfer(Transfer transfer, Account source_account, Account destination_account,
			User user) {
		
		if(isExists(source_account.getNumber()) && isExists(destination_account.getNumber())) {
			
			transaction(transfer, source_account, destination_account);
			transfer.setUser_transfer(user);			
			return transfer;
		}
		
		else throw new AccountDoesntExistException("One or both accounts doesnt exist, Please check account numbers and try again! ");
				

	}
	
	
	
	
	/* transfer transaction **/
	public void transaction(Transfer transfer, Account source_account, Account destination_account) {
		
		source_account.setBalance(source_account.getBalance().subtract(transfer.getAmount_to_send()));
		destination_account.setBalance(destination_account.getBalance().add(transfer.getAmount_to_send()));

	}

	/* checks if there is balance money for you to send funds **/
	public boolean not_have_enough_balance(TransferRequest transferRequest, Account source_account) {
		
		return source_account.getBalance().compareTo(transferRequest.getAmount_to_send()) != 1;
	}
	
	/* check if exists by account number **/
	private boolean isExists(String accountNum) {
		if(accountRepo.existsByNumber(accountNum)) {
			return true;
		}
		else return false;
	}

}
