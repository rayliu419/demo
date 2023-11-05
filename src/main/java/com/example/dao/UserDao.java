package com.example.dao;

import java.util.Optional;

public interface UserDao {

    public Optional<String> getDistrictByUserId(String id);
}
