package com.example.demo.openapi.domain;

import javax.validation.constraints.DecimalMin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
	private long id;
	private String description;
	@DecimalMin(value="0.01")
	private Double price;
}
