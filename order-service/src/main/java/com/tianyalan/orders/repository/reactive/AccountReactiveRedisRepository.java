package com.tianyalan.orders.repository.reactive;

import com.tianyalan.orders.model.Account;

import reactor.core.publisher.Mono;

public interface AccountReactiveRedisRepository {
    Mono<Boolean> saveAccount(Account account);
    
    Mono<Boolean> updateAccount(Account account);
    
    Mono<Boolean> deleteAccount(String accountId);
    
    Mono<Account> findAccountById(String accountId);
}


