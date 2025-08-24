package e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;

@Configuration
public class SwaggerConfig {
	  @Bean
	    public OpenAPI customOpenAPI() {
	        final String securitySchemeName = "bearerAuth";

	        return new OpenAPI()
	                .info(new Info()
	                        .title("E-Commerce API")
	                        .version("1.0")
	                        .description("API documentation for the E-Commerce project (Users, Products, Cart, Orders, Payments)"))
	                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
	                .components(new Components()
	                        .addSecuritySchemes(securitySchemeName,
	                                new SecurityScheme()
	                                        .name(securitySchemeName)
	                                        .type(SecurityScheme.Type.HTTP)
	                                        .scheme("bearer")
	                                        .bearerFormat("JWT")
	                                        .in(In.HEADER)));
	    }
}
