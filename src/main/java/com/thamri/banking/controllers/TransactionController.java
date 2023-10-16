package com.thamri.banking.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thamri.banking.dto.TransactionDto;
import com.thamri.banking.services.TransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name="transaction")
public class TransactionController {
	
	private final TransactionService service;
	
	
	@PostMapping("/")
	public ResponseEntity<Integer> save (@RequestBody TransactionDto transactionDto){
		return ResponseEntity.ok(service.save(transactionDto));
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TransactionDto>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{transaction-id}")
	public ResponseEntity<TransactionDto> findById(@PathVariable("transaction-id") Integer TransactionID){
		return ResponseEntity.ok(service.findById(TransactionID));
	}
	
	@GetMapping("/users/{user-id}")
	public ResponseEntity<List<TransactionDto>> findAllByUserId(@PathVariable("user-id") Integer userId){
		return ResponseEntity.ok(service.findAllByUserId(userId));
	}
	
	@DeleteMapping("/{transaction-id}")
	public ResponseEntity<Void> delete(@PathVariable("transaction-id") Integer TransactionID){
		return ResponseEntity.accepted().build();
	}
			
}
