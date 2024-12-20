package org.backend.developer.assignment.service;

import org.backend.developer.assignment.api.dto.request.ProductRequest;
import org.backend.developer.assignment.api.dto.response.ProductResponse;
import org.backend.developer.assignment.exception.ProductNotFoundException;

import java.util.List;

/**
 * Service interface for managing product-related operations.
 * Provides methods for CRUD operations on products.
 */
public interface ProductService {

	/**
	 * Retrieves a list of all products available in the system.
	 *
	 * @return a list of ProductResponse objects representing the products.
	 */
	List<ProductResponse> getAllProducts();

	/**
	 * Retrieves a product by its unique identifier.
	 *
	 * @param id the unique identifier of the product to retrieve
	 * @return the product details wrapped in a ProductResponse object
	 * @throws ProductNotFoundException if a product with the specified id is not found
	 */
	ProductResponse getProductById(Long id) throws ProductNotFoundException;

	/**
	 * Creates a new product based on the provided product details.
	 *
	 * @param request the details of the product to be created, encapsulated in a ProductRequest object
	 * @return the created product encapsulated in a ProductResponse object
	 */
	ProductResponse createProduct(ProductRequest request);

	/**
	 * Updates an existing product with new details.
	 *
	 * @param id The ID of the product to update.
	 * @param request The updated product details including name, description, price, and active status.
	 * @return The updated product details.
	 * @throws ProductNotFoundException If the product with the specified ID does not exist.
	 */
	ProductResponse updateProduct(Long id, ProductRequest request) throws ProductNotFoundException;

	/**
	 * Deletes a product identified by its unique ID.
	 *
	 * @param id the unique identifier of the product to be deleted
	 * @throws ProductNotFoundException if no product with the specified ID exists
	 */
	void deleteProduct(Long id) throws ProductNotFoundException;
}
