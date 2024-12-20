package org.backend.developer.assignment.service.impl;

import lombok.RequiredArgsConstructor;
import org.backend.developer.assignment.api.dto.request.ProductRequest;
import org.backend.developer.assignment.api.dto.response.ProductResponse;
import org.backend.developer.assignment.exception.ProductNotFoundException;
import org.backend.developer.assignment.mapper.ProductMapper;
import org.backend.developer.assignment.model.Product;
import org.backend.developer.assignment.repository.ProductRepository;
import org.backend.developer.assignment.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductMapper mapper;
	private final ProductRepository productRepository;


	@Transactional(readOnly = true)
	public List<ProductResponse> getAllProducts() {
		final var products = productRepository.findAll();

		return mapper.from(products);
	}

	@Transactional(readOnly = true)
	public ProductResponse getProductById(Long id) throws ProductNotFoundException {
		final var product = getProduct(id);

		return mapper.from(product);
	}

	@Transactional
	public ProductResponse createProduct(ProductRequest request) {
		final var product = mapper.to(request);

		final var entity = productRepository.save(product);

		return mapper.from(entity);
	}

	@Transactional
	public ProductResponse updateProduct(Long id, ProductRequest productDetails) throws ProductNotFoundException {
		final var product = getProduct(id);

		mapper.updateEntity(product, productDetails);

		final var entity = productRepository.save(product);

		return mapper.from(entity);
	}

	@Transactional
	public void deleteProduct(Long id) throws ProductNotFoundException {
		final var product = getProduct(id);

		productRepository.delete(product);
	}

	private Product getProduct(Long id) throws ProductNotFoundException {
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
	}
}
