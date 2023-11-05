package com.example.interceptor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LocalInterceptor {

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logControllerMethods() {
        System.out.println("Local Interceptor for RestController");
    }

}
