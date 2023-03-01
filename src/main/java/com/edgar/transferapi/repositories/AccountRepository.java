package com.edgar.transferapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.transferapi.models.Account;
import com.edgar.transferapi.models.user.User;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	boolean existsByNumber (String number);
	
	Optional<Account> findByNumber(String number); //check if present
	
	List<Account> findByUser(User user);
	
	
	
	

}
