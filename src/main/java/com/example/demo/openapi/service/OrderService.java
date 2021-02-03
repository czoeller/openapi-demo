package com.example.demo.openapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.openapi.domain.Order;
import com.google.common.collect.Lists;

@Service
public class OrderService {

	private static final List<Order> orders = Lists.newArrayList(
			new Order(1, "a", 1.42),
			new Order(2, "b", 9.99)
	);
	private long orderId;

	public OrderService() {
		orderId = getHighestId();
	}

	private long getHighestId() {
		return orders.stream().mapToLong(Order::getId).summaryStatistics().getMax();
	}

	public List<Order> findAll() {
		return orders;
	}

	public Optional<Order> findById(long orderId) {
		return orders.stream().filter(order -> order.getId() == orderId).findFirst();
	}

	public Order add(Order order) {
		order.setId(++orderId);
		orders.add(order);
		return order;
	}
}
