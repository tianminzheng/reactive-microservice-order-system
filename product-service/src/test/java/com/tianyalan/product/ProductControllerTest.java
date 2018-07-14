package com.tianyalan.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.tianyalan.product.controllers.ProductController;
import com.tianyalan.product.model.Product;
import com.tianyalan.product.services.ProductService;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = ProductController.class)
public class ProductControllerTest {

	@Autowired
	WebTestClient webClient;

	@MockBean
	ProductService service;

	@Test
	public void testGetByProductCode() {
		Product mockProduct = new Product("001", "Book001", "Microservie Practices",
				"New Book For Microservie By Tianyalan", 100F);

		given(service.getProductByCode("Book001")).willReturn(Mono.just(mockProduct));

		EntityExchangeResult<Product> result = webClient.get()
				.uri("http://localhost:8081/v1/products/{productCode}", "Book001").exchange().expectStatus()
				.isOk().expectBody(Product.class).returnResult();

		verify(service).getProductByCode("Book001");
		verifyNoMoreInteractions(service);
		
		assertThat(result.getResponseBody().getProductCode()).isEqualTo("Book001");
	}
	
	
	@Test
	public void testDeleteProduct() {
		given(service.deleteProductById("001")).willReturn(Mono.empty());

		webClient.delete()
				.uri("http://localhost:8081/v1/products/{productId}", "001").exchange().expectStatus()
				.isOk().expectBody(Void.class).returnResult();

		verify(service).deleteProductById("001");
		verifyNoMoreInteractions(service);
	}

}

