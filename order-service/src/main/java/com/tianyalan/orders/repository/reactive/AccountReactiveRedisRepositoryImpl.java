package com.tianyalan.orders.repository.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;

import com.tianyalan.orders.model.Account;

import reactor.core.publisher.Mono;

@Repository
public class AccountReactiveRedisRepositoryImpl implements AccountReactiveRedisRepository {

	@Autowired
	private ReactiveRedisTemplate<String, Account> reactiveRedisTemplate;

	private static final String HASH_NAME = "Account:";

	@Override
	public Mono<Boolean> saveAccount(Account account) {
		return reactiveRedisTemplate.opsForValue().set(HASH_NAME + account.getId(), account);
	}

	@Override
	public Mono<Boolean> updateAccount(Account account) {
		return reactiveRedisTemplate.opsForValue().set(HASH_NAME + account.getId(), account);
	}

	@Override
	public Mono<Boolean> deleteAccount(String accountId) {
		return reactiveRedisTemplate.opsForValue().delete(HASH_NAME + accountId);
	}

	@Override
	public Mono<Account> findAccountById(String accountId) {
		return reactiveRedisTemplate.opsForValue().get(HASH_NAME + accountId);
	}
}
