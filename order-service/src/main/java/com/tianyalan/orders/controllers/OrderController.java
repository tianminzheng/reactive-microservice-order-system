package com.tianyalan.orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tianyalan.orders.model.Order;
import com.tianyalan.orders.services.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class OrderController {
	
    @Autowired
    private OrderService orderService;
	
    @PostMapping("/v1/orders/{accountId}/{productCode}")
	public Mono<Order> saveOrder( @PathVariable("accountId") String accountId,
            @PathVariable("productCode") String productCode) {
		Mono<Order> order = orderService.addOrder(accountId, productCode);		
		
		return order;
	}
	
    @GetMapping("/v1/orders/{id}")
    public Mono<Order> getOrder(@PathVariable String id) {
		Mono<Order> order = orderService.getOrderById(id);
		
    	return order;
    }

    @GetMapping("/v1/orders")
	public Flux<Order> getOrderList() {
		Flux<Order> orders = orderService.getOrders();
		
		return orders;
	}
	
}
