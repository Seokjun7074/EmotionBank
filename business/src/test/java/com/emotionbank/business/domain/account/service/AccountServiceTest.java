package com.emotionbank.business.domain.account.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.emotionbank.business.domain.account.dto.AccountDto;
import com.emotionbank.business.domain.account.repository.AccountRepository;
import com.emotionbank.business.domain.user.entity.User;
import com.emotionbank.business.domain.user.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;


	@Test
	void 계좌생성() {
		// Given
		AccountService accountService = new AccounServiceImpl(accountRepository, userRepository);
		assertNotNull(accountService);
		User user = userRepository.save(User.builder()
			.nickname("TEST NAME")
			.build());
		String accountName = "테스트용계좌";
		// When
		AccountDto accountDto = accountService.createAccount(user.getUserId(), accountName);

		// Then
		assertEquals(accountDto.getAccountId(), 1L);
		assertEquals(accountDto.getAccountName(), accountName);
		assertEquals(accountDto.getUserId(), user.getUserId());
		assertEquals(accountDto.getAccountNumber().length(),"yyMM-ddHH-mmssSSS".length());
		assertEquals(accountDto.getBalance(), 0L);

		// 확인용 출력
		// System.out.println(accountDto.getAccountId());
		// System.out.println(accountDto.getAccountName());
		// System.out.println(accountDto.getAccountNumber());
		// System.out.println(accountDto.getBalance());
		// System.out.println(accountDto.getCreatedTime());
	}
}