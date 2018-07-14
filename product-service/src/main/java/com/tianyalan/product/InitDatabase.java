
package com.tianyalan.product;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.tianyalan.product.model.Product;

@Component
public class InitDatabase {
	@Bean
	CommandLineRunner init(MongoOperations operations) {
		return args -> {
			operations.dropCollection(Product.class);

			operations.insert(new Product("Product" + UUID.randomUUID().toString(),"001", "Microservie Practices",
					"New Book For Microservie By Tianyalan", 100F));
			operations.insert(new Product("Product" + UUID.randomUUID().toString(),"002", "Microservie Design",
					"Another New Book For Microservie By Tianyalan", 200F));
			
			operations.findAll(Product.class).forEach(product -> {
				System.out.println(product.toString());
			});
		};
	}
}