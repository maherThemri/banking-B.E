package com.thamri.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.banking.models.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
