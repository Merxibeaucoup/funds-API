package com.edgar.transferapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.transferapi.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	boolean existsByNumber (String number);
	
	Optional<Account> findByNumber(String number); //check if present

}
