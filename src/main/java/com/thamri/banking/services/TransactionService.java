package com.thamri.banking.services;

import java.util.List;

import com.thamri.banking.dto.TransactionDto;

public interface TransactionService extends AbstractService<TransactionDto> {

	List<TransactionDto> findAllByUserId(Integer userId);
}
