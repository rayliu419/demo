package com.example.debug;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BeanInspector {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void printBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            System.out.println("Bean Name: " + beanName + ", Bean Class: " + bean.getClass().getName());
        }
    }
}
