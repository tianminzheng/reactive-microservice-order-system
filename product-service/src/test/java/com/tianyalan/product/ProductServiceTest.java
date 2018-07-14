package com.tianyalan.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tianyalan.product.model.Product;
import com.tianyalan.product.repository.reactive.ProductReactiveRepository;
import com.tianyalan.product.services.ProductService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	ProductService service;
	
	@MockBean
	ProductReactiveRepository repository;

	@Test
	public void testGetByProductCode() {
		Product mockProduct = new Product("001", "Book001", "Microservie Practices",
				"New Book For Microservie By Tianyalan", 100F);
		
		given(repository.getByProductCode("Book001")).willReturn(Mono.just(mockProduct));
		
		Mono<Product> product = service.getProductByCode("Book001");
		
		StepVerifier.create(product).expectNextMatches(results -> {
			assertThat(results.getProductCode()).isEqualTo("Book001");
			assertThat(results.getProductName()).isEqualTo("Microservie Practices");
			return true;
		}).verifyComplete();
		
	}	
}


