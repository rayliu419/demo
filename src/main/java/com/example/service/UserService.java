package com.example.service;

import java.util.Optional;

public interface UserService {

    public Optional<String> getUserByUserId(String id);
}
