package com.example.dao;

import com.example.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    boolean createUser(UserEntity user);

    Optional<String> getUserByUserId(String id);

    List<UserEntity> getListByName(String name);

    boolean updateUser(UserEntity user);

    boolean deleteUserByUserId(String id);
}
