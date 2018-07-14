package com.tianyalan.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.tianyalan.product.model.Product;
import com.tianyalan.product.repository.reactive.ProductReactiveRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
@DataMongoTest
public class EmbeddedProductRepositoryTest {

	@Autowired
	ProductReactiveRepository repository;

	@Autowired
	MongoOperations operations;
	
	@Before
	public void setUp() {
		operations.dropCollection(Product.class);

		operations.insert(new Product("Product" + UUID.randomUUID().toString(),"Book001", "Microservie Practices",
				"New Book For Microservie By Tianyalan", 100F));
		operations.insert(new Product("Product" + UUID.randomUUID().toString(),"Book002", "Microservie Design",
				"Another New Book For Microservie By Tianyalan", 200F));
		
		operations.findAll(Product.class).forEach(product -> {
			System.out.println(product.toString());
		});
	}
	
	@Test
	public void testGetByProductCode() {
		Mono<Product> product = repository.getByProductCode("Book001");

		StepVerifier.create(product)
			.expectNextMatches(results -> {
					assertThat(results.getProductCode()).isEqualTo("Book001");
					assertThat(results.getProductName()).isEqualTo("Microservie Practices");
					return true;
			});
	}
}
