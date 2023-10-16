package com.thamri.banking.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.thamri.banking.dto.TransactionSumDetails;

public interface StatisticsService {
	
	List<TransactionSumDetails> findSumTransactionsByDate(LocalDate startDate, LocalDate endDate, Integer userId);

	BigDecimal getAccountBalance(Integer userId);
	
	BigDecimal highestTransfer(Integer userId);
	
	BigDecimal highestDeposit(Integer userId);
}
