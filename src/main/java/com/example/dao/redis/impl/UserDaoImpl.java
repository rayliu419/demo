package com.example.dao.redis.impl;

import com.example.dao.UserDao;
import com.example.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * docker启动redis:
 * docker run -p 6379:6379 -d redis:latest redis-server
 * docker exec -it {redis_container_id} bash
 *
 */
@Component
// 条件初始化为UserDao的一个实现
@ConditionalOnProperty(name = "db.type", havingValue = "redis")
@Log4j2
public class UserDaoImpl implements UserDao {

    /**
     * 由于直接使用的spring-boot-starter-data-redis，所以RedisTemplate被初始化后可以直接使用
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean createUser(User user) {
        try {
            String value = objectMapper.writeValueAsString(user);
            System.out.println(String.format("id - %s, value - %s", user.getId(), value));
            redisTemplate.opsForValue().set(user.getId(), value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<String> getUserByUserId(String id) {
        return Optional.empty();
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUserByUserId(String id) {
        return false;
    }
}
