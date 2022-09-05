package com.edgar.transferapi.services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.edgar.transferapi.models.Account;
import com.edgar.transferapi.models.Transfer;
import com.edgar.transferapi.models.User;
import com.edgar.transferapi.requests.TransferRequest;

@Service
public class TransferService {
	
	public Transfer transfer(TransferRequest transferRequest, Account source_account, Account destination_account , User user ) {
		
		transaction(transferRequest, source_account, destination_account); //invoke transaction method
		
		Transfer transfer = new Transfer();
		// source -> target
		BeanUtils.copyProperties(transferRequest, transfer);
		
		transfer.setUser_transfer(user);
		
		return transfer;
		
	}
	
	
	public void transaction(TransferRequest transferRequest, Account source_account, Account destination_account) {
		
		
		
		// subtract sent amount from source account balance
		source_account.setBalance(source_account.getBalance().subtract(transferRequest.getAmount_to_send()));
		
		
		// add sent amount to the destination account balance
		
		destination_account.setBalance(destination_account.getBalance().add(transferRequest.getAmount_to_send()));
		
	}

}
