package com.tianyalan.account.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.account.model.Account;
import com.tianyalan.account.services.AccountService;

import reactor.core.publisher.Mono;

@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@GetMapping("/v1/accounts/{accountId}")
	public Mono<Account> getAccount(@PathVariable("accountId") String accountId) {		
		 Mono<Account> account = accountService.getAccountById(accountId);
		 return account;
	}

	@PutMapping("/v1/accounts")
	public void updateAccount(@RequestBody Account account) {
		accountService.updateAccount(account);
	}

	@PostMapping("/v1/accounts")
	public void saveAccount(@RequestBody Account account) {
		accountService.updateAccount(account);
	}

	@DeleteMapping("/v1/accounts/{accountId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAccount(@PathVariable("accountId") String accountId) {
		Account account = new Account();
		account.setId(accountId);

		accountService.deleteAccount(account);
	}
}
