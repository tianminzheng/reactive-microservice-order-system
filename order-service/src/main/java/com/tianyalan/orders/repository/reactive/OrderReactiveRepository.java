package com.tianyalan.orders.repository.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.tianyalan.orders.model.Order;

public interface OrderReactiveRepository extends ReactiveMongoRepository<Order, String> {

}
