package com.thamri.banking.services;

import java.util.List;

import com.thamri.banking.dto.ContactDto;

public interface ContactService extends AbstractService<ContactDto> {

	List<ContactDto> findAllByUserId(Integer userId);
}
