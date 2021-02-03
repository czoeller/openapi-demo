package com.example.demo.openapi.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.openapi.domain.Order;
import com.example.demo.openapi.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Operation(operationId = "allOrders", summary = "Get all orders")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation")
	})
	@GetMapping("/orders")
	public List<Order> all() {
		return orderService.findAll();
	}

	@Operation(operationId = "findOrderById", summary = "Find order by it's id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation"),
			@ApiResponse(responseCode = "404", description = "not found")
	})
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> findById(@PathVariable Long orderId) {
		Optional<Order> order = orderService.findById(orderId);
		if (order.isPresent()) {
			return new ResponseEntity<>(order.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(operationId = "addOrder", summary = "Add a new order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Order.class))) })
	@PostMapping(value = "/orders")
	public ResponseEntity<Order> add(@RequestBody @Valid Order order) {
		Order savedOrder;
		try {
			savedOrder = orderService.add(order);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class).findById(savedOrder.getId())).toUri());
		return new ResponseEntity<>(savedOrder, httpHeaders, HttpStatus.CREATED);
	}

}
