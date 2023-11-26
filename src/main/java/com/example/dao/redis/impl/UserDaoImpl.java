package com.example.dao.redis.impl;

import com.example.dao.UserDao;
import com.example.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


/**
 * docker启动redis:
 * docker run -p 6379:6379 -d redis:latest redis-server
 * 进入容器：
 * docker exec -it {redis_container_id} bash
 * 运行redis命令行
 *
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
    public boolean createUser(UserEntity user) {
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
    public List<UserEntity> getListByName(String name) {
        return null;
    }

    @Override
    public boolean updateUser(UserEntity user) {
        return false;
    }

    @Override
    public boolean deleteUserByUserId(String id) {
        return false;
    }
}
