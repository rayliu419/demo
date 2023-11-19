package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.entity.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Optional<String> getUserByUserId(String id) {
        return userDao.getUserByUserId(id);
    }

    @Override
    public boolean createUser(User user) {
        System.out.println("Service");
        return userDao.createUser(user);
    }
}
