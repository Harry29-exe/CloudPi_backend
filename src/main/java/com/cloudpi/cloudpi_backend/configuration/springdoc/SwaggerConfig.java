package com.cloudpi.cloudpi_backend.configuration.springdoc;

import com.cloudpi.cloudpi_backend.user.controllers.UserAPIAuthorities;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "CloudPiAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@OpenAPIDefinition(
        info = @Info(title = "CloudPi API", version = "0.0.1")
)
public class SwaggerConfig {
    //    @Bean
//    public Docket configureSwagger() {
//        return new Docket(DocumentationType.OAS_30)
//                .securityContexts(List.of(securityContext()))
//                .select()
//                .build();
//    }
//
//    private SecurityContext securityContext () {
//        return SecurityContext.builder()
//                .securityReferences(references())
//                .build();
//    }
//
//    private List<SecurityReference> references() {
//        SecurityReference securityReference = new SecurityReference(
//                "JWT",
//                new AuthorizationScope[]{new AuthorizationScope("global", "global scope")}
//        );
//        return List.of(securityReference);
//    }

}
