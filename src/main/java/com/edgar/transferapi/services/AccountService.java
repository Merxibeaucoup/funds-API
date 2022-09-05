package com.edgar.transferapi.services;

import org.springframework.stereotype.Service;

import com.edgar.transferapi.models.Account;
import com.edgar.transferapi.requests.TransferRequest;

@Service
public class AccountService {
	
	
	// checks if there is balance money for you to send funds
	public boolean not_have_enough_balance(TransferRequest transferRequest, Account source_account) {
        return source_account.getBalance().compareTo(transferRequest.getAmount_to_send()) != 1;
    }

}
