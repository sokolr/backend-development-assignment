package org.backend.developer.assignment.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.backend.developer.assignment.api.dto.request.ProductRequest;
import org.backend.developer.assignment.api.dto.response.ProductResponse;
import org.backend.developer.assignment.exception.ProductNotFoundException;
import org.backend.developer.assignment.mapper.ProductMapper;
import org.backend.developer.assignment.model.Product;
import org.backend.developer.assignment.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class ProductServiceImplTest {

	private final ProductRepository productRepository = mock(ProductRepository.class);

	private final ProductMapper productMapper = mock(ProductMapper.class);

	private final ProductServiceImpl productService = new ProductServiceImpl(productMapper, productRepository);


	@Test
	void getAllProducts_ReturnsListOfProducts() {
		List<Product> products = List.of(new Product());
		List<ProductResponse> productResponses = List.of(new ProductResponse(1L, "Product", "Desc", 100.0));

		when(productRepository.findAll()).thenReturn(products);
		when(productMapper.from(products)).thenReturn(productResponses);

		List<ProductResponse> response = productService.getAllProducts();

		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(productResponses, response)
		);
		verify(productRepository).findAll();
		verify(productMapper).from(products);
	}

	@Test
	void getProductById_ExistingId_ReturnsProduct() throws Exception {
		Long productId = 1L;
		Product product = new Product();
		ProductResponse productResponse = new ProductResponse(1L, "Product", "Desc", 100.0);

		when(productRepository.findById(productId)).thenReturn(Optional.of(product));
		when(productMapper.from(product)).thenReturn(productResponse);

		ProductResponse response = productService.getProductById(productId);


		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(productResponse, response)
		);
		verify(productRepository).findById(productId);
		verify(productMapper).from(product);
	}

	@Test
	void getProductById_NonExistentId_ThrowsProductNotFoundException() {
		Long productId = 1L;

		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
		verify(productRepository).findById(productId);
	}

	@Test
	void createProduct_SavesAndReturnsProduct() {
		ProductRequest productRequest = new ProductRequest("Name", "Desc", 100.0);
		Product productToSave = new Product();
		Product savedProduct = new Product();
		ProductResponse productResponse = new ProductResponse(1L, "Name", "Desc", 100.0);

		when(productMapper.to(productRequest)).thenReturn(productToSave);
		when(productRepository.save(productToSave)).thenReturn(savedProduct);
		when(productMapper.from(savedProduct)).thenReturn(productResponse);

		ProductResponse response = productService.createProduct(productRequest);

		assertAll(
				() -> assertNotNull(response),
				() -> assertEquals(productResponse, response)
		);
		verify(productMapper).to(productRequest);
		verify(productRepository).save(productToSave);
		verify(productMapper).from(savedProduct);
	}

	@Test
	void deleteProduct_ExistingId_DeletesProduct() throws Exception {
		Long productId = 1L;
		Product product = new Product();

		when(productRepository.findById(productId)).thenReturn(Optional.of(product));
		doNothing().when(productRepository).delete(product);

		productService.deleteProduct(productId);

		verify(productRepository).findById(productId);
		verify(productRepository).delete(product);
	}

	@Test
	void deleteProduct_NonExistentId_ThrowsProductNotFoundException() {
		Long productId = 1L;

		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
		verify(productRepository).findById(productId);
		verify(productRepository, never()).delete(any(Product.class));
	}
}