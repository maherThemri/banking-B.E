package com.thamri.banking.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thamri.banking.config.JwtUtils;
import com.thamri.banking.dto.AccountDto;
import com.thamri.banking.dto.AuthenticationRequest;
import com.thamri.banking.dto.AuthenticationResponse;
import com.thamri.banking.dto.UserDto;
import com.thamri.banking.models.Role;
import com.thamri.banking.models.User;
import com.thamri.banking.repositories.RoleRepository;
import com.thamri.banking.repositories.UserRepository;
import com.thamri.banking.services.AccountService;
import com.thamri.banking.services.UserService;
import com.thamri.banking.validators.ObjectsValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private static final String ROLE_USER = "ROLE_USER";
	private final UserRepository repository;
	private final AccountService accountService;
	private final ObjectsValidator<UserDto> validator;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final AuthenticationManager authManager;
	private final RoleRepository roleRepository;

	@Override
	public Integer save(UserDto dto) {
		validator.validate(dto);
		User user =UserDto.toEntity(dto);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return repository.save(user).getId();
	}

	@Override
	public List<UserDto> findAll() {
		return repository.findAll()
				.stream()
				.map(UserDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public UserDto findById(Integer id) {
		
		return repository.findById(id)
				.map(UserDto::fromEntity)
				.orElseThrow(()-> new EntityNotFoundException("No user was found with the provided Id: " + id));
	}

	@Override
	public void delete(Integer id) {
		// TODO check before delete
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public Integer validateAccount(Integer id) {
		User user = repository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException
						("No user was found for user account validation : "+id));
		user.setActive(true);
		//create a bank account 
		AccountDto account = AccountDto.builder()
				.user(UserDto.fromEntity(user))
				.build();
		accountService.save(account);

		repository.save(user);
		repository.save(user);
		return user.getId();
	}

	@Override
	public Integer invalidateAccount(Integer id) {
		User user = repository.findById(id)
				.orElseThrow(()-> new EntityNotFoundException
						("No user was found for user account validation : "+id));
		user.setActive(false);
		repository.save(user);
		return user.getId();
	}

	@Override
	@Transactional
	public AuthenticationResponse register(UserDto dto) {
		validator.validate(dto);
		User user =UserDto.toEntity(dto);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(findOrCreateRole(ROLE_USER));
		var savedUser = repository.save(user);
		
		String token= jwtUtils.generateToken(savedUser);
		
		return AuthenticationResponse.builder()
				.token(token)
				.build();
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		final UserDetails user = repository.findByEmail(request.getEmail()).get();
		final String token =  jwtUtils.generateToken(user);
		
		return AuthenticationResponse.builder().token(token).build();
	}

	private Role findOrCreateRole(String roleName) {
		Role role = roleRepository.findByName(roleName)
				.orElse(null);
		if(role == null) {
			return roleRepository.save(Role.builder().name(roleName).build());
		}
		return role;
	}
}
