package com.edgar.transferapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.transferapi.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
