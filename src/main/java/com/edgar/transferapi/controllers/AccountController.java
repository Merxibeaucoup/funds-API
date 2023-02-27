package com.edgar.transferapi.controllers;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edgar.transferapi.models.Account;
import com.edgar.transferapi.models.Transfer;
import com.edgar.transferapi.models.user.User;
import com.edgar.transferapi.repositories.AccountRepository;
import com.edgar.transferapi.repositories.TransferRepository;
import com.edgar.transferapi.repsonse.MessageResponse;
import com.edgar.transferapi.requests.DepositRequest;
import com.edgar.transferapi.requests.TransferRequest;
import com.edgar.transferapi.services.AccountService;
import com.edgar.transferapi.services.TransferService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
	
	
	@Autowired
    AccountRepository accountRepository;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    TransferService transferService;

   

    @Autowired
    AccountService accountService;
    
    
    /* This is a test **/
    @GetMapping
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("Helloo from the secured endpoint");
	}
	
	/* register account **/
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount( @Valid @RequestBody Account account, @AuthenticationPrincipal User user){   
    	
    	return ResponseEntity.ok(accountService.newAccount(account, user));
    	
    }
    
    
    
    
    @PutMapping("/deposit")
   public ResponseEntity<?> deposit( @Valid @RequestBody DepositRequest deposit,  @AuthenticationPrincipal User user){  
    	
    	Account account = accountService.getAccountByNumber(deposit.getAccountNumber()); 	
    	accountService.makeDeposit(deposit, account );
    	return new ResponseEntity<>(account, HttpStatus.OK);
    }
    
    
	
	
	
	
	//make transfer
    @Transactional
    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@Valid @RequestBody TransferRequest transferRequest){
    	
    	MessageResponse error = new MessageResponse(); // use throughout with out multi declaration 
    	
    	
    	Optional<Account> source_account_optional = accountRepository.findByNumber(transferRequest.getFrom_account_number());
    	Optional<Account> destination_account_optional = accountRepository.findByNumber(transferRequest.getDestination_account_number());
    	
    	
    
    	
    	
    	boolean source_account_not_found = !source_account_optional.isPresent(); 
    	boolean destination_account_not_found = !destination_account_optional.isPresent();
    	
    	
    	
    	
    	
    	
    	if(source_account_not_found) {
    		error.setError("Source account not found for this user!");
    		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    	}
    	
    	if(destination_account_not_found) {
    		error.setError("There is no such account in our system, please verify and try again");
    		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    	}
    	
    	
    	Account source_account = source_account_optional.get(); 
    	Account destination_account = destination_account_optional.get(); 
    	  	
        User user = new User();
        Transfer transfer = transferService.transfer(transferRequest, source_account, destination_account, user);
        
       
        accountRepository.save(source_account);
        accountRepository.save(destination_account);
        
       
        transferRepository.save(transfer);
        
        
        
        return new ResponseEntity<>(transfer, HttpStatus.CREATED); //return response
        
        
    }
    
    //get balance

}
