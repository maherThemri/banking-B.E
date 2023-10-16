package com.thamri.banking.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import com.thamri.banking.dto.AccountDto;
import com.thamri.banking.exceptions.OperationNonPermittedException;
import com.thamri.banking.models.Account;
import com.thamri.banking.repositories.AccountRepository;
import com.thamri.banking.services.AccountService;
import com.thamri.banking.validators.ObjectsValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AccountServiceImpl implements AccountService {
	
	private final AccountRepository repository;
	private final ObjectsValidator<AccountDto> validator;
	
	@Override
	public Integer save(AccountDto dto) {
		//block account update -> iban cannot be changed
		/*if(dto.getId() != null) {
			throw new OperationNonPermittedException(
					"Account cannot be updated",
					"save account",
					"Account",
					"Update not permitted");
		}*/
		validator.validate(dto);
		Account account = AccountDto.toEntity(dto);
		
		boolean userHasAlreadyAnAccount = repository.findByUserId(account.getUser().getId()).isPresent();
		if(userHasAlreadyAnAccount && account.getUser().isActive()) {
			throw new OperationNonPermittedException(
					"The selected user has already an active account",
					"Create Account",
					"Account service",
					"Account creation");
		}
		// generate random IBAN when creating new account else do not update the IBAN
		if(dto.getId() == null) {
		//  generate random IBAN
		account.setIban(generateRandomIban());
		}
		return repository.save(account).getId();
	}

	@Override
	public List<AccountDto> findAll() {
		
		return repository.findAll()
				.stream().map(AccountDto::fromEntity).collect(Collectors.toList());
	}

	@Override
	public AccountDto findById(Integer id) {
		
		return repository.findById(id)
				.map(AccountDto::fromEntity)
				.orElseThrow(()-> new EntityNotFoundException("No account was found with the ID: "+id));
	}

	@Override
	public void delete(Integer id) {
		// TODO check delete account
		repository.deleteById(id);

	}
	
	private String generateRandomIban() {
		
		 //generate an Iban
		String iban = Iban.random(CountryCode.DE).toFormattedString();
		//check if the iban already exists
		boolean ibanExists = repository.findByIban(iban).isPresent();
		// if exists -> generate new random iban
		if(ibanExists) {
			generateRandomIban();
		}
		//if not exist => return generated iban
		return iban;
	}

}
