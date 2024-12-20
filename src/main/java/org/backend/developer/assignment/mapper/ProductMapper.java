package org.backend.developer.assignment.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.backend.developer.assignment.api.dto.request.ProductRequest;
import org.backend.developer.assignment.api.dto.response.ProductResponse;
import org.backend.developer.assignment.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = SPRING)
public interface ProductMapper {

	List<ProductResponse> from(List<Product> products);

	ProductResponse from(Product product);

	@Mapping(target = "id", ignore = true)
	Product to(ProductRequest product);

	@Mapping(target = "id", ignore = true)
	void updateEntity(@MappingTarget Product entity, ProductRequest request);
}
