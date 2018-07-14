package com.tianyalan.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Product {
	@Id
	private String id;
	
	private String productCode;
	private String productName;
	private String description;
	private Float price;
	
}

