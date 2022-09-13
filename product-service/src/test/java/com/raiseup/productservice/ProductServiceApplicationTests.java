package com.raiseup.productservice;

import com.raiseup.productservice.dto.ProductRequest;
import com.raiseup.productservice.dto.ProductResponse;
import com.raiseup.productservice.model.Product;
import com.raiseup.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@RequiredArgsConstructor
class ProductServiceApplicationTests {
	private final ProductService productService;

	final MongoDBContainer mongoDBContainer =
			new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

	@BeforeEach
	public void startMongo(){
		mongoDBContainer.start();
	}
	@AfterEach
	public void stopMongo(){
		mongoDBContainer.stop();
	}

	@Test
	void contextLoads() {
		Product product = Product.builder().build();
		ProductResponse productResponse = ProductResponse.builder().build();
		ProductRequest productRequest = ProductRequest.builder().build();

	}


}
