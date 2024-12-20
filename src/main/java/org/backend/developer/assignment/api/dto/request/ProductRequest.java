package org.backend.developer.assignment.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(
		@NotBlank(message = "Product name must not be empty")
		@Size(min = 1, max = 100)
		String name,
		String description,
		@NotNull
		@Min(value = 0, message = "Price must be greater than or equal to 0")
		Double price
) {
}
