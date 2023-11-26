package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
@MapperScan("com.example.dao.mysql.mapper") // mybatis的Scan Mapper
@EnableJpaRepositories(basePackages = "com.example.dao.mysql.repository")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
