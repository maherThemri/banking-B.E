package com.thamri.banking.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.thamri.banking.dto.AddressDto;
import com.thamri.banking.models.Address;
import com.thamri.banking.repositories.AddressRepository;
import com.thamri.banking.services.AddressService;
import com.thamri.banking.validators.ObjectsValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
	
	private final AddressRepository repository;
	private final ObjectsValidator<AddressDto> validator;
	
	@Override
	public Integer save(AddressDto dto) {
		validator.validate(dto);
		Address address = AddressDto.toEntity(dto);
		return repository.save(address).getId();
	}

	@Override
	public List<AddressDto> findAll() {
		
		return repository.findAll().stream().map(AddressDto::fromEntity).collect(Collectors.toList());
	}

	@Override
	public AddressDto findById(Integer id) {
		
		return repository.findById(id).map(AddressDto::fromEntity)
				.orElseThrow(()-> new EntityNotFoundException("No address found with the ID : "+id));
	}

	@Override
	public void delete(Integer id) {
		// TODO check delete
		repository.deleteById(id);

	}

}
