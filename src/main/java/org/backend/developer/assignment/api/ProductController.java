package org.backend.developer.assignment.api;

import static org.springframework.http.HttpStatus.CREATED;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.developer.assignment.api.dto.request.ProductRequest;
import org.backend.developer.assignment.api.dto.response.ProductResponse;
import org.backend.developer.assignment.exception.ProductNotFoundException;
import org.backend.developer.assignment.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Products", description = "API for managing Products")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "Get all products",
			description = "Returns all products")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Successfully retrieved the list of products"),
			@ApiResponse(responseCode = "400",
					description = "Invalid request supplied")
	})
	@GetMapping
	public List<ProductResponse> getAllProducts() {
		return productService.getAllProducts();
	}

	@Operation(summary = "Get existing product",
			description = "Returns a product if exists")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Successfully retrieved the product by id"),
			@ApiResponse(responseCode = "400",
					description = "Invalid request supplied"),
			@ApiResponse(responseCode = "404",
					description = "Product not found")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		try {
			ProductResponse product = productService.getProductById(id);
			return ResponseEntity.ok(product);
		} catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Create a new product",
			description = "Creates a new product with the given details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201",
					description = "Successfully created the product"),
			@ApiResponse(responseCode = "400",
					description = "Invalid product data supplied")})
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
		final var product = productService.createProduct(request);
		return ResponseEntity.status(CREATED).body(product);
	}

	@Operation(summary = "Update existing product",
			description = "Updates a product with the given details if it exists")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Successfully updated the product"),
			@ApiResponse(responseCode = "400",
					description = "Invalid product data supplied"),
			@ApiResponse(responseCode = "404",
					description = "Product not found")})
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
	                                                     @RequestBody @Valid ProductRequest request) {
		try {
			ProductResponse updatedProduct = productService.updateProduct(id, request);
			return ResponseEntity.ok(updatedProduct);
		} catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Delete a product",
			description = "Deletes a product with the specified ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204",
					description = "Successfully deleted the product"),
			@ApiResponse(responseCode = "404",
					description = "Product not found")})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		try {
			productService.deleteProduct(id);
		} catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
}
