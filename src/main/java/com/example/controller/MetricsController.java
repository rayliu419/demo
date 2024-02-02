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
 *
 */

@RestController
@RequestMapping("metrics")
@Slf4j
public class MetricsController {

    @Timed(value = "metrics api-1")
    @GetMapping("/api-1")
    public ResponseEntity<String> api1() {
        // 使用Random类生成随机数
        Random random = new Random();
        int randomValue = random.nextInt(100); // 生成0到9的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue < 20) { // 例如，当随机数小于2时，返回5xx错误
            return new ResponseEntity<>("api-1: Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            // 模拟正常情况
            return new ResponseEntity<>("api-1: Success", HttpStatus.OK);
        }
    }

    @Timed(value = "metrics api-2")
    @GetMapping("/api-2")
    public ResponseEntity<String> api2() {
        // 使用Random类生成随机数
        Random random = new Random();
        int randomValue = random.nextInt(100); // 生成0到9的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue < 10) { // 例如，当随机数小于2时，返回5xx错误
            return new ResponseEntity<>("api-2: Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            // 模拟正常情况
            return new ResponseEntity<>("api-2: Success", HttpStatus.OK);
        }
    }

    @Timed(value = "metrics api-3")
    @GetMapping("/api-3")
    public ResponseEntity<String> api3() {
        // 使用Random类生成随机数
        Random random = new Random();
        int randomValue = random.nextInt(100); // 生成0到9的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue < 5) { // 例如，当随机数小于2时，返回5xx错误
            return new ResponseEntity<>("api-3: Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            // 模拟正常情况
            return new ResponseEntity<>("api-3: Success", HttpStatus.OK);
        }
    }

    @Timed(value = "metrics api-4")
    @GetMapping("/api-4")
    public ResponseEntity<String> api4() {
        // 使用Random类生成随机数
        Random random = new Random();
        int randomValue = random.nextInt(100); // 生成0到9的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue < 3) { // 例如，当随机数小于2时，返回5xx错误
            return new ResponseEntity<>("api-4: Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            // 模拟正常情况
            return new ResponseEntity<>("api-4: Success", HttpStatus.OK);
        }
    }

    @Timed(value = "metrics api-5")
    @GetMapping("/api-5")
    public ResponseEntity<String> api5() {
        // 使用Random类生成随机数
        Random random = new Random();
        int randomValue = random.nextInt(100); // 生成0到9的随机整数
        // 模拟在一定概率下返回5xx错误
        if (randomValue < 1) { // 例如，当随机数小于2时，返回5xx错误
            return new ResponseEntity<>("api-5: Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            // 模拟正常情况
            return new ResponseEntity<>("api-5: Success", HttpStatus.OK);
        }
    }
}
