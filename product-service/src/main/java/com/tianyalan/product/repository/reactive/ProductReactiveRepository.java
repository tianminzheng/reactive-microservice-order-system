package com.tianyalan.product.repository.reactive;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.tianyalan.product.model.Product;

import reactor.core.publisher.Mono;

public interface ProductReactiveRepository 
	extends ReactiveMongoRepository<Product, String> {

	Mono<Product> getByProductCode(String productCode);	
}




//public interface ProductReactiveRepository 
//	extends ReactiveCrudRepository<Product, String> {
//
//	Mono<Product> getByProductCode(String productCode);	
//}

