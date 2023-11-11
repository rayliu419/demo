package com.example.redis.dao;

import com.example.dao.UserDao;
import com.example.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ConditionalOnProperty(name = "db.type", havingValue = "redis")
public class UserDaoImpl implements UserDao {

    @Override
    public boolean createUser(User user) {
        return false;
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
