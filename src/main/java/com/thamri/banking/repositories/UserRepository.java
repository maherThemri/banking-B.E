package com.thamri.banking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.banking.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);

}
