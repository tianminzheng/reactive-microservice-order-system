package com.tianyalan.orders.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.tianyalan.orders.model.Account;
import com.tianyalan.orders.repository.reactive.AccountReactiveRedisRepository;

import reactor.core.publisher.Mono;

@Component
public class AccountReactiveClient {

    private static final Logger logger = LoggerFactory.getLogger(AccountReactiveClient.class);
    
    @Autowired
    AccountReactiveRedisRepository accountReactiveRedisRepository;
    
    private Mono<Account> getAccountFromCache(String accountId) {
        try {
        	logger.info("Get account from redis: {}", accountId);
        	
            return accountReactiveRedisRepository.findAccountById(accountId);
        }
        catch (Exception ex){
        	logger.error("Get account from redis failed: {}", accountId);
        	
            return null;
        }
    }

    private void putAccountIntoCache(Mono<Account> account) {
    	Account blockedAccount = account.block();
    	
        try {        	
        	logger.info("Put account into redis: {}", blockedAccount.getId());
        	
        	accountReactiveRedisRepository.saveAccount(blockedAccount);
        }catch (Exception ex){
        	logger.error("Put account into redis failed: {}", blockedAccount.getId());
        }
    }
   
    public Mono<Account> getAccount(String accountId){
    	
    	logger.debug("Get account: {}", accountId);
    	
    	Mono<Account> account = getAccountFromCache(accountId);
        if (account.block()!= null){
            return account;
        }
        
        Mono<Account> accountMono = WebClient.create()
                .get()
                .uri("http://localhost:5555/api/account/v1/accounts/{accountId}", accountId) 
                .retrieve()
                .bodyToMono(Account.class);
        
        if (accountMono != null) {
        	putAccountIntoCache(accountMono);
        }
        
        return accountMono;
    }
    
}
