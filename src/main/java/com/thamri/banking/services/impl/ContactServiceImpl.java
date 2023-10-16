package com.thamri.banking.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.thamri.banking.dto.ContactDto;
import com.thamri.banking.models.Contact;
import com.thamri.banking.repositories.ContactRepository;
import com.thamri.banking.services.ContactService;
import com.thamri.banking.validators.ObjectsValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
	
	private final ContactRepository repository;
	private final ObjectsValidator<ContactDto> validator;
	
	@Override
	public Integer save(ContactDto dto) {
		validator.validate(dto);
		Contact contact = ContactDto.toEntity(dto); 
		return repository.save(contact).getId();
	}

	@Override
	public List<ContactDto> findAll() {
		
		return repository.findAll().stream().map(ContactDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public ContactDto findById(Integer id) {
		
		return repository.findById(id).map(ContactDto::fromEntity)
				.orElseThrow(()-> new EntityNotFoundException("No contact was found with the ID : "+id));
	}

	@Override
	public void delete(Integer id) {
	    repository.deleteById(id);

	}

	@Override
	public List<ContactDto> findAllByUserId(Integer userId) {
		return repository.findAllByUserId(userId)
				.stream()
				.map(ContactDto::fromEntity)
				.collect(Collectors.toList());
	}

}
