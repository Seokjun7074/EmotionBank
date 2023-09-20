package com.emotionbank.business.api.transaction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emotionbank.business.api.transaction.dto.GetTransactionDetailDto;
import com.emotionbank.business.api.transaction.dto.GetTransactionListDto;
import com.emotionbank.business.api.transaction.dto.UpdateBalanceDto;
import com.emotionbank.business.domain.transaction.constant.TransactionType;
import com.emotionbank.business.domain.transaction.dto.TransactionDto;
import com.emotionbank.business.domain.transaction.dto.TransactionSearchDto;
import com.emotionbank.business.domain.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping
	public ResponseEntity<UpdateBalanceDto.Response> updateBalance(
		@RequestBody UpdateBalanceDto.Request request) {
		TransactionDto transactionDto = transactionService.updateBalance(TransactionDto.from(request));
		return ResponseEntity.ok(UpdateBalanceDto.Response.from(transactionDto));
	}

	@GetMapping
	public ResponseEntity<GetTransactionListDto.Response> getTransactions(
		@RequestParam Long accountId, @RequestParam String startDate, @RequestParam String endDate) {
		List<TransactionDto> transactionList = transactionService.getTransactions(
			TransactionSearchDto.of(accountId, startDate, endDate));
		return ResponseEntity.ok(GetTransactionListDto.Response.from(transactionList));
	}

	@GetMapping("/{transactionId}")
	public ResponseEntity<GetTransactionDetailDto.Response> getTransactionDetail(@PathVariable Long transactionId) {
		TransactionDto transactionDto = transactionService.getTransactionDetail(transactionId);

		GetTransactionDetailDto.Response response = null;

		if (TransactionType.DEPOSIT.equals(transactionDto.getTransactionType())) {
			response = GetTransactionDetailDto.Response.of(transactionDto, transactionDto.getSender());
		} else if (TransactionType.WITHDRAWL.equals(transactionDto.getTransactionType())) {
			response = GetTransactionDetailDto.Response.of(transactionDto, transactionDto.getReceiver());
		}

		if (response == null) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(response);
	}

}