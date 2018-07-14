package com.tianyalan.account.repository.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.tianyalan.account.model.Account;

public interface AccountReactiveRepository extends ReactiveMongoRepository<Account, String> { 

}
