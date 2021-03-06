package com.tianyalan.orders.events.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.tianyalan.orders.events.AccountChangeChannel;
import com.tianyalan.orders.events.models.AccountChangeModel;
import com.tianyalan.orders.repository.reactive.AccountReactiveRedisRepository;

@EnableBinding(AccountChangeChannel.class)
public class AccountChangeHandler {

	@Autowired
    AccountReactiveRedisRepository accountReactiveRedisRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountChangeHandler.class);

    @StreamListener("inboundAccountChanges")
    public void accountChangeSink(AccountChangeModel accountChange) {
    	
        logger.debug("Received a message of type " + accountChange.getType()); 
    	logger.debug("Received a {} event from the account service for account id {}", 
    			accountChange.getAction(), 
    			accountChange.getAccount().getId());
        
        if(accountChange.getAction().equals("SAVE")) {
        	accountReactiveRedisRepository.saveAccount(accountChange.getAccount());
        } else if(accountChange.getAction().equals("UPDATE")) {
        	accountReactiveRedisRepository.updateAccount(accountChange.getAccount());        	
        } else if(accountChange.getAction().equals("DELETE")) {
        	accountReactiveRedisRepository.deleteAccount(accountChange.getAccount().getId());
        } else {            
            logger.error("Received an UNKNOWN event from the account service of type {}", accountChange.getType());
        }
    }
}
