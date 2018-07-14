package com.tianyalan.orders.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.tianyalan.orders.model.Product;

import reactor.core.publisher.Mono;

@Component
public class ProductReactiveClient {
  
    private static final Logger logger = LoggerFactory.getLogger(ProductReactiveClient.class);    
    
    public Mono<Product> getProduct(String productCode) {
    	
    	logger.debug("Get product: {}", productCode);
    	
    	Mono<Product> product = WebClient.create()
                .get()
                .uri("http://localhost:5555/api/product/v1/products/{productCode}", productCode)
                .retrieve()
                .bodyToMono(Product.class);

        return product;
    }
}


