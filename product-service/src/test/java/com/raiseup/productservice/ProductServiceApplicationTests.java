package com.raiseup.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raiseup.productservice.dto.ProductRequest;
import com.raiseup.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RequiredArgsConstructor
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer =
			new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data..mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	ProductRepository productRepository;

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = ProductRequest.builder()
				.name("product")
				.description("product description")
				.price(BigDecimal.valueOf(12.99))
				.build();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productRequest))
				).andExpect(status().isCreated());
		Assertions.assertTrue(productRepository.findAll().size()>1);
	}

	@Test
	void shouldGetProduct() throws Exception {
		ProductRequest productRequest1 = ProductRequest.builder()
				.name("product")
				.description("product description")
				.price(BigDecimal.valueOf(12.99))
				.build();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productRequest1))
		).andExpect(status().isCreated());

		ProductRequest productRequest2 = ProductRequest.builder()
				.name("product")
				.description("product description")
				.price(BigDecimal.valueOf(12.99))
				.build();
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productRequest2))
		).andExpect(status().isCreated());


		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/product")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		Assertions.assertTrue(productRepository.findAll().size()>0);
	}


}
