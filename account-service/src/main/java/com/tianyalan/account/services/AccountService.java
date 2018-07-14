package com.tianyalan.account.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianyalan.account.events.source.AccountChangeSource;
import com.tianyalan.account.model.Account;
import com.tianyalan.account.repository.reactive.AccountReactiveRepository;

import reactor.core.publisher.Mono;

@Service
public class AccountService {
    @Autowired
    private AccountReactiveRepository accountReactiveRepository;

    @Autowired
    private AccountChangeSource accountChangeSource;

    public Mono<Account> getAccountById(String accountId) {
        return accountReactiveRepository.findById(accountId);
    }

    public void saveAccount(Account account){
    	Mono<Account> saveAccount = accountReactiveRepository.save(account);
    	saveAccount.subscribe();
        
        accountChangeSource.publishAccountAddedEvent(account);
    }

    public void updateAccount(Account account){
    	Mono<Account> saveAccount = accountReactiveRepository.save(account);
    	saveAccount.subscribe();
    	
    	accountChangeSource.publishAccountUpdatedEvent(account);
    }

    public void deleteAccount(Account account){
    	Mono<Void> deleteAccount = accountReactiveRepository.delete(account);
    	deleteAccount.subscribe();
    	
    	accountChangeSource.publishAccountDeletedEvent(account);
    }
}
