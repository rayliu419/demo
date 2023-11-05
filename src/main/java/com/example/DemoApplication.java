package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import org.example.ThirdLibHello;
import org.springframework.context.annotation.Import;

/**
 * To import external jar, build the artifact of external jar.
 * 	https://www.jetbrains.com/help/idea/compiling-applications.html#package_into_jar
 * In this project, add external jar to the dependencies.
 * 	https://www.geeksforgeeks.org/how-to-add-external-jar-file-to-an-intellij-idea-project/
 *
 */

/**
 * 默认情况下，运行此application只会扫描当前包和子包的Bean。
 */
@SpringBootApplication
@EnableAspectJAutoProxy
// 让第三方Bean生效的第一种方法：直接初始化第三方库的切面Bean
// @Import(org.example.interceptor.ThirdLibLogInterceptor.class)
public class DemoApplication {

	public static void main(String[] args) {
		new ThirdLibHello().testHello();
		SpringApplication.run(DemoApplication.class, args);
	}
}
