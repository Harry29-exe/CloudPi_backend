package com.cloudpi.cloudpi_backend.security.handler_interceptors;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

//@Component
public class AdditionalSecurityHandlerInterceptor
//        implements HandlerInterceptor
{


    //    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkLocalNetworkRequirement(request, handler);
    }


    private boolean checkLocalNetworkRequirement(HttpServletRequest request, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            throw new IllegalArgumentException("Handler object should be instance of HandlerMethod");
        }
        request.getRemoteAddr();


        return true;
    }

    protected <T extends Annotation> @Nullable T getMethodAnnotation(HandlerMethod handler, Class<T> annotation) {
        return handler.getMethod().getAnnotation(annotation);
    }
}
