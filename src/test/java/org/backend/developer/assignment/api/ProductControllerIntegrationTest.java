package org.backend.developer.assignment.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.developer.assignment.api.dto.request.ProductRequest;
import org.backend.developer.assignment.api.dto.response.ProductResponse;
import org.backend.developer.assignment.exception.ProductNotFoundException;
import org.backend.developer.assignment.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ProductController.class)
class ProductControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private ProductService productService;

	@Test
	void getAllProducts_ShouldReturnProductList() throws Exception {
		var firstProductName = "Product 1";
		var firstProductDescription = "Some product";
		var firstProductPrice = 100.0;

		var secondProductName = "Product 2";
		var secondProductDescription = "Very good product";
		var secondProductPrice = 150.0;

		List<ProductResponse> products = Arrays.asList(
				new ProductResponse(1L, firstProductName, firstProductDescription, firstProductPrice),
				new ProductResponse(2L, secondProductName, secondProductDescription, secondProductPrice)
		);

		given(productService.getAllProducts()).willReturn(products);

		mockMvc.perform(get("/products")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is(firstProductName)))
				.andExpect(jsonPath("$[0].description", is(firstProductDescription)))
				.andExpect(jsonPath("$[0].price", is(firstProductPrice)))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is(secondProductName)))
				.andExpect(jsonPath("$[1].description", is(secondProductDescription)))
				.andExpect(jsonPath("$[1].price", is(secondProductPrice)));
	}

	@Test
	void getProductById_ValidId_ShouldReturnProduct() throws Exception {
		var id = 1L;
		var name = "Product";
		var description = "Some product";
		var price = 100.0;
		ProductResponse product = new ProductResponse(id, name, description, price);

		given(productService.getProductById(id)).willReturn(product);

		mockMvc.perform(get("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(name)))
				.andExpect(jsonPath("$.description", is(description)))
				.andExpect(jsonPath("$.price", is(price)));
	}

	@Test
	void getProductById_InvalidId_ShouldReturnNotFound() throws Exception {
		var id = 1L;
		given(productService.getProductById(id)).willThrow(ProductNotFoundException.class);

		mockMvc.perform(get("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void getProductById_InvalidRequest_ShouldReturnBadRequest() throws Exception {
		Long id = null;

		mockMvc.perform(get("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createProduct_ValidRequest_ShouldReturnCreatedProduct() throws Exception {
		var id = 1L;
		var name = "Product";
		var description = "Some product";
		var price = 100.0;
		ProductRequest request = new ProductRequest(name, description, price);
		ProductResponse response = new ProductResponse(id, name, description, price);

		given(productService.createProduct(any(ProductRequest.class))).willReturn(response);

		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(name)))
				.andExpect(jsonPath("$.description", is(description)))
				.andExpect(jsonPath("$.price", is(price)));
	}

	@Test
	void createProduct_InvalidRequest_ShouldReturnBadRequest() throws Exception {
		ProductRequest request = new ProductRequest(null, null, null); // Invalid name, price

		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void updateProduct_ValidRequest_ShouldReturnUpdatedProduct() throws Exception {
		var id = 1L;
		var name = "Updated Product";
		var description = "Product description updated";
		var price = 120.0;

		ProductRequest request = new ProductRequest(name, description, price);
		ProductResponse response = new ProductResponse(id, name, description, price);

		given(productService.updateProduct(eq(id), any(ProductRequest.class))).willReturn(response);

		mockMvc.perform(put("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(name)))
				.andExpect(jsonPath("$.description", is(description)))
				.andExpect(jsonPath("$.price", is(price)));
	}


	@Test
	void updateProduct__InvalidRequest_ShouldReturnBadRequest() throws Exception {
		var id = 1L;
		ProductRequest request = new ProductRequest(null, null, null);

		mockMvc.perform(put("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void updateProduct_InvalidId_ShouldReturnNotFound() throws Exception {
		var id = 1L;

		ProductRequest request = new ProductRequest("Updated Product", "", 120.0);

		given(productService.updateProduct(eq(id), any(ProductRequest.class))).willThrow(ProductNotFoundException.class);

		mockMvc.perform(put("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteProduct_ValidId_ShouldReturnNoContent() throws Exception {
		var id = 1L;

		mockMvc.perform(delete("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	void deleteProduct_InvalidId_ShouldReturnNotFound() throws Exception {
		var id = 1L;

		doThrow(ProductNotFoundException.class).when(productService).deleteProduct(id);

		mockMvc.perform(delete("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteProduct_InvalidId_ShouldReturnBadRequest() throws Exception {
		Long id = null;

		mockMvc.perform(delete("/products/" + id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}