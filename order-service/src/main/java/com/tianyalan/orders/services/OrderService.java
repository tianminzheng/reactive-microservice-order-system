package com.tianyalan.orders.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tianyalan.orders.clients.AccountReactiveClient;
import com.tianyalan.orders.clients.ProductReactiveClient;
import com.tianyalan.orders.model.Account;
import com.tianyalan.orders.model.Order;
import com.tianyalan.orders.model.Product;
import com.tianyalan.orders.repository.reactive.OrderReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

	@Autowired
	private OrderReactiveRepository orderRepository;

	@Autowired
	private ProductReactiveClient productClient;

	@Autowired
	private AccountReactiveClient accountClient;	

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@HystrixCommand
	private Mono<Account> getAccount(String accountId) {

		return accountClient.getAccount(accountId);
	}

	@HystrixCommand
	private Mono<Product> getProduct(String productCode) {

		return productClient.getProduct(productCode);
	}

	public Mono<Order> addOrder(String accountId, String productCode) {
		
		logger.debug("addOrder with account: {} and product: {}", accountId, productCode);
		
		Order order = new Order();
		
		order.setId("Order" + UUID.randomUUID().toString());

		Product product = getProduct(productCode).block();
		if (product == null) {
			return Mono.just(order);
		}
		
		logger.debug("get product: {} is successful", productCode);

		Account account = getAccount(accountId).block();
		if (account == null) {
			return Mono.just(order);
		}
		
		logger.debug("get account: {} is successful", accountId);

		order.setAccountId(accountId);
		order.setItem(product.getProductName());
		order.setCreateTime(new Date());

		Mono<Order> saveOrder = orderRepository.save(order);
//		saveOrder.subscribe();

		return saveOrder;
	}
	
	@HystrixCommand(fallbackMethod = "getOrdersFallback")
	public Flux<Order> getOrders() {
		return orderRepository.findAll();
	}

	@SuppressWarnings("unused")	
	private Flux<Order> getOrdersFallback() {
		List<Order> fallbackList = new ArrayList<>();

		Order order = new Order();
		order.setId("OrderInvalidId");
		order.setAccountId("InvalidId" );
		order.setItem("Order list is not available");
		order.setCreateTime(new Date());
		fallbackList.add(order);
		
		return Flux.fromIterable(fallbackList);
	}
	
	@SuppressWarnings("unused")	
	private Mono<Order> getOrderFallback() {		
		Order order = new Order();
		order.setId("OrderInvalidId");
		order.setAccountId("InvalidId" );
		order.setItem("Order list is not available");
		order.setCreateTime(new Date());
			
		return Mono.just(order);
	}

	public Mono<Order> getOrderById(String id) {
		return orderRepository.findById(id);
	}
}
