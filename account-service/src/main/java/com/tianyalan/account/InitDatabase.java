
package com.tianyalan.account;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.tianyalan.account.model.Account;

@Component
public class InitDatabase {
	@Bean
	CommandLineRunner init(MongoOperations operations) {
		return args -> {
			operations.dropCollection(Account.class);

			operations.insert(new Account("Account" + UUID.randomUUID().toString(),"tianyalan1", "tianmin zheng1"));
			operations.insert(new Account("Account" + UUID.randomUUID().toString(),"tianyalan2", "tianmin zheng2"));
			
			operations.findAll(Account.class).forEach(product -> {
				System.out.println(product.toString());
			});
		};
	}
}