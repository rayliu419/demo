package com.example;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @RestController = @Controller + @ResponseBody
 *
 * @Controller is used to create web controllers that return views, which is further resolved by view resolver,
 * while @RestController is used to create web services that return JSON or XML data.
 * Now we always use @RestController because we just return json data.
 *
 */
@RestController
public class HelloController {

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        userService.getUserByUserId("111");
        return String.format("Hello %s!", name);
    }
}
