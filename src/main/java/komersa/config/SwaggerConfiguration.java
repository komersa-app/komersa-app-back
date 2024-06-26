package komersa.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
/*
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
        .info(apiInfo());
  }

  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer");
  }

 */

  private Info apiInfo() {
    return new Info()
        .title("Komersa Api Doc")
        .version("1.0.0")
        .description("HTTP APIs for managing e-commerce resources")
        .contact(new Contact().name("Komersa Inc"));
  }
  // TODO: configure openapi yaml file generator
  // link : ui - http://localhost:8080/swagger-ui/index.html
  //        specs - /v3/api-docs
}
