package com.example.service;

import com.example.dto.domain.UserBO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public Optional<String> getUserByUserId(String id);

    public boolean createUser(UserBO userBO);

    public List<UserBO> getListByName(String name);
}
