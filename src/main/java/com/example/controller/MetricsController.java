package com.example.controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 这个类不实现具体的功能，其主要用于生成指标来测试Grafana的PromSQL的语法。
 */

@RestController
@RequestMapping("metrics")
@Slf4j
public class MetricsController {

    @Timed(value = "metrics api-1")
    @GetMapping("/api-1")
    public ResponseEntity<String> api1() throws InterruptedException {
        // 使用Random类生成随机数
        Random random = new Random();
        // 随机睡眠时间
        Thread.sleep(random.nextInt(5) + 5);
        int randomValue = random.nextInt(100); // 生成0到100的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue > 90) {
            // 例如，当随机数大于90时，返回错误
            return new ResponseEntity<>("api-1: Internal Server Error", HttpStatus.BAD_GATEWAY);
        }
        if (randomValue > 80) {
            return new ResponseEntity<>("api-1: Internal Server Error", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (randomValue > 70) {
            return new ResponseEntity<>("api-1: Internal Server Error", HttpStatus.GATEWAY_TIMEOUT);
        }
        if (randomValue > 60) {
            return new ResponseEntity<>("api-1: Client Error", HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE);
        }
        if (randomValue > 50) {
            return new ResponseEntity<>("api-1: Client Error", HttpStatus.TOO_MANY_REQUESTS);
        }

        // 模拟正常情况
        return new ResponseEntity<>("api-1: Success", HttpStatus.OK);
    }

    @Timed(value = "metrics api-2")
    @GetMapping("/api-2")
    public ResponseEntity<String> api2() throws InterruptedException {
        // 使用Random类生成随机数
        Random random = new Random();
        Thread.sleep(random.nextInt(10) + 5);
        int randomValue = random.nextInt(100); // 生成0到9的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue > 95) {
            // 例如，当随机数大于90时，返回错误
            return new ResponseEntity<>("api-2: Internal Server Error", HttpStatus.BAD_GATEWAY);
        }
        if (randomValue > 90) {
            return new ResponseEntity<>("api-2: Internal Server Error", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (randomValue > 80) {
            return new ResponseEntity<>("api-2: Internal Server Error", HttpStatus.GATEWAY_TIMEOUT);
        }
        if (randomValue > 70) {
            return new ResponseEntity<>("api-2: Internal Server Error", HttpStatus.UPGRADE_REQUIRED);
        }
        if (randomValue > 60) {
            return new ResponseEntity<>("api-2: Internal Server Error", HttpStatus.PRECONDITION_REQUIRED);
        }

        // 模拟正常情况
        return new ResponseEntity<>("api-2: Success", HttpStatus.OK);
    }

    @Timed(value = "metrics api-3")
    @GetMapping("/api-3")
    public ResponseEntity<String> api3() throws InterruptedException {
        // 使用Random类生成随机数
        Random random = new Random();
        Thread.sleep(random.nextInt(20) + 5);
        int randomValue = random.nextInt(100); // 生成0到9的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue > 91) {
            // 例如，当随机数大于90时，返回错误
            return new ResponseEntity<>("api-3: Internal Server Error", HttpStatus.BAD_GATEWAY);
        }
        if (randomValue > 85) {
            return new ResponseEntity<>("api-3: Internal Server Error", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (randomValue > 77) {
            return new ResponseEntity<>("api-3: Internal Server Error", HttpStatus.GATEWAY_TIMEOUT);
        }
        // 模拟正常情况
        return new ResponseEntity<>("api-3: Success", HttpStatus.OK);
    }

    @Timed(value = "metrics api-4")
    @GetMapping("/api-4")
    public ResponseEntity<String> api4() throws InterruptedException {
        // 使用Random类生成随机数
        Random random = new Random();
        Thread.sleep(random.nextInt(30) + 5);
        int randomValue = random.nextInt(100); // 生成0到9的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue > 90) {
            // 例如，当随机数大于90时，返回错误
            return new ResponseEntity<>("api-4: Internal Server Error", HttpStatus.BAD_GATEWAY);
        }
        if (randomValue > 80) {
            return new ResponseEntity<>("api-4: Internal Server Error", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (randomValue > 70) {
            return new ResponseEntity<>("api-4: Internal Server Error", HttpStatus.GATEWAY_TIMEOUT);
        }
        // 模拟正常情况
        return new ResponseEntity<>("api-4: Success", HttpStatus.OK);
    }

    @Timed(value = "metrics api-5")
    @GetMapping("/api-5")
    public ResponseEntity<String> api5() throws InterruptedException {
        Random random = new Random();
        Thread.sleep(random.nextInt(50) + 5);
        // 模拟正常情况
        return new ResponseEntity<>("api-5: Success", HttpStatus.OK);
    }
}
