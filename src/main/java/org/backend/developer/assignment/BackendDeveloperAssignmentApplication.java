package org.backend.developer.assignment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Product API", version = "1.0", description = "API for managing products"))
@SpringBootApplication
public class BackendDeveloperAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDeveloperAssignmentApplication.class, args);
	}

}
