package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 让第三方Bean生效的第二种方法：用一个configuration类来ComponentScan，会把这个包下的所有Bean都初始化
 * @Import(org.example.interceptor.ThirdLibLogInterceptor.class)
 */
@Configuration
//@ComponentScan(basePackages = {"org.example.interceptor"})
public class ExternalLibConfig {
}
