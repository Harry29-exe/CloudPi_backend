package com.cloudpi.cloudpi_backend.configuration.springdoc;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

@Configuration
public class SpringdocSecurityAuthentication {
    @Bean
    public OperationCustomizer operationCustomizer() {
        //TODO(Analyze code copied from stack overflow
        // stack link:
        // https://stackoverflow.com/questions/65876585/automatic-swagger-annotation-based-description-generation-in-springdoc)
        return (operation, handlerMethod) -> {
            Optional<PreAuthorize> preAuthorizeAnnotation = Optional.ofNullable(handlerMethod.getMethodAnnotation(PreAuthorize.class));
            StringBuilder sb = new StringBuilder();
            if (preAuthorizeAnnotation.isPresent()) {
                sb.append("This api requires **")
                        .append((preAuthorizeAnnotation.get()).value().replaceAll("hasAuthority|\\(|\\)|\\'", ""))
                        .append("** permission.");
            } else {
                sb.append("This api is **public**");
            }
            sb.append("<br /><br />");
            if (operation.getDescription() != null) {
                sb.append(operation.getDescription());
            }
            operation.setDescription(sb.toString());
            return operation;
        };
    }
}