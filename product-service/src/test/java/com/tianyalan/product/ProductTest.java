package com.tianyalan.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.tianyalan.product.model.Product;

public class ProductTest {

	@Test
	public void testLombok() {

		Product product = new Product("001", "Book001", "Microservie Practices",
				"New Book For Microservie By Tianyalan", 100F);

		assertThat(product.getId()).isEqualTo("001");
		assertThat(product.getProductCode()).isEqualTo("Book001");
		assertThat(product.getPrice()).isEqualTo(100F);
	}
}


