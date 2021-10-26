package com.cloudpi.cloudpi_backend.test.utils;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithMockUser(username = "we2", setupBefore = TestExecutionEvent.TEST_EXECUTION)
public @interface WithUser {

    @AliasFor(value = "value", annotation = WithMockUser.class)
    String value() default "user";

    @AliasFor(value = "username", annotation = WithMockUser.class)
    String username() default "";

    @AliasFor(value = "roles", annotation = WithMockUser.class)
    String[] roles() default { "USER" };

    @AliasFor(value = "authorities", annotation = WithMockUser.class)
    String[] authorities() default {};

    @AliasFor(value = "password", annotation = WithMockUser.class)
    String password() default "password";

}
