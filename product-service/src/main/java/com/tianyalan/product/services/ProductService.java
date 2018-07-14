package com.tianyalan.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import com.tianyalan.product.model.Product;
import com.tianyalan.product.repository.reactive.ProductReactiveRepository;

import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
	
	@Autowired
	private ProductReactiveRepository productReactiveRepository;
		
	@Autowired
    private Tracer tracer;
	
	public Mono<Product> getProductByCode(String productCode) {
				
		Span newSpan = tracer.createSpan("getProductByCode");
		
		try {			
			return productReactiveRepository.getByProductCode(productCode);
        }
        finally{
          newSpan.tag("peer.service", "database");
          newSpan.logEvent(org.springframework.cloud.sleuth.Span.CLIENT_RECV);
          tracer.close(newSpan);
        }
	}
	
	@Autowired
	private MeterRegistry meterRegistry;
	
	public Mono<Void> deleteProductById(String id) {
		Mono<Void> deleteProduct = productReactiveRepository.deleteById(id);
		
		Mono<Void> countDeletedProduct = Mono.fromRunnable(() -> {
			meterRegistry.summary("products.deleted.count").record(1);
		});
		
		return Mono.when(deleteProduct, countDeletedProduct);
	}
	
	
}
