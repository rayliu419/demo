package com.example.service;

import com.example.entity.User;

import java.util.Optional;

public interface UserService {

    public Optional<String> getUserByUserId(String id);

    public boolean createUser(User user);
}
