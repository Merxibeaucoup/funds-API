package com.edgar.transferapi.controllers;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edgar.transferapi.models.Account;
import com.edgar.transferapi.models.Transfer;
import com.edgar.transferapi.models.User;
import com.edgar.transferapi.repositories.AccountRepository;
import com.edgar.transferapi.repositories.TransferRepository;
import com.edgar.transferapi.repsonse.MessageResponse;
import com.edgar.transferapi.requests.TransferRequest;
import com.edgar.transferapi.services.AccountService;
import com.edgar.transferapi.services.TransferService;
import com.edgar.transferapi.services.UserService;

@RestController
@RequestMapping("/api/account")
public class AccountController {@Autowired
    AccountRepository accountRepository;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    TransferService transferService;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;
	
	//register account
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount( @Valid @RequestBody Account account){
    	
    	if(accountRepository.existsByNumber(account.getAccount_number())) {
    		MessageResponse error = new MessageResponse("An Account with this  number already exists");
    		return new  ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    	}
    	
    	// TODO needs spring security to get logged in user details
    //User user = userService.getUser(null) //:( idk man
    	
    	User user = new User();
    	
    	
    	account.setUser(user);
    	accountRepository.save(account);
    	return ResponseEntity.ok(account);
    	
    }
	
	
	
	
	//make transfer
    @Transactional
    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@Valid @RequestBody TransferRequest transferRequest){
    	
    	MessageResponse error = new MessageResponse(); // use throughout with out multi declaration 
    	
    	/*
    	 * @optional --> bruh
    	 * 
    	 * may or may not be available .. so check
    	 * 
    	 * It's possible that no such entity exists, so using an 
    	 * Optional return type is a good idea since it forces whoever is 
    	 * calling the method to consider the empty scenario. 
    	 * This reduces chances of a NullPointerException.
    	 */
    	
    	Optional<Account> source_account_optional = accountRepository.findByNumber(transferRequest.getFrom_account_number());
    	Optional<Account> destination_account_optional = accountRepository.findByNumber(transferRequest.getDestination_account_number());
    	
    	
    	//check if accounts are valid/present 
    	
    	
    	boolean source_account_not_found = !source_account_optional.isPresent(); // return true if not found
    	boolean destination_account_not_found = !destination_account_optional.isPresent(); // return true if not found
    	
    	
    	
    	//if not valid/present , throw errors
    	
    	
    	if(source_account_not_found) {
    		error.setError("Source account not found for this user!");
    		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    	}
    	
    	if(destination_account_not_found) {
    		error.setError("There is no such account in our system, please verify and try again");
    		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    	}
    	
    	
    	Account source_account = source_account_optional.get(); // if source account is present, get it
    	Account destination_account = destination_account_optional.get();  // if destination account is present, get it
    	
    	
    	
    	
    	// TODO needs spring security to get logged in user details
        User user = new User(); //:( idk man
        Transfer transfer = transferService.transfer(transferRequest, source_account, destination_account, user);
        
        // save  accounts  info     
        accountRepository.save(source_account);
        accountRepository.save(destination_account);
        
        //save transaction info
        transferRepository.save(transfer);
        
        
        
        return new ResponseEntity<>(transfer, HttpStatus.CREATED); //return response
        
        
    }
    
    //get balance

}
