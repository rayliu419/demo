package com.example.dao;

import com.example.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface UserDao {

    boolean createUser(User user);

    Optional<String> getUserByUserId(String id);

    boolean updateUser(User user);

    boolean deleteUserByUserId(String id);
}
