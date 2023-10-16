package com.thamri.banking.services;

import com.thamri.banking.dto.AuthenticationRequest;
import com.thamri.banking.dto.AuthenticationResponse;
import com.thamri.banking.dto.UserDto;

public interface UserService extends AbstractService<UserDto>{

	Integer validateAccount(Integer id);
	
	Integer invalidateAccount(Integer id);

	AuthenticationResponse register(UserDto user);

	AuthenticationResponse authenticate(AuthenticationRequest request);
	
}
