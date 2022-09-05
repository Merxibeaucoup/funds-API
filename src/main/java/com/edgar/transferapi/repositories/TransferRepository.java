package com.edgar.transferapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.transferapi.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, String> {
	
	

}
