package com.thamri.banking.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thamri.banking.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role>findByName(String roleName);

}
