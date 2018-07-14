package com.tianyalan.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.product.model.Product;
import com.tianyalan.product.services.ProductService;

import reactor.core.publisher.Mono;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;
    
    @DeleteMapping("/v1/products/{productId}")
    public Mono<Void> deleteProduct(@PathVariable String productId) {		
		
		Mono<Void> result = productService.deleteProductById(productId);
    	return result;
    }
    

	
    @GetMapping("/v1/products/{productCode}")
    public Mono<Product> getProduct(@PathVariable String productCode) {		
    	
		Mono<Product> product = productService.getProductByCode(productCode);
    	return product;
    }
}
