package com.example.service;

import com.example.dto.domain.UserBO;
import com.example.dto.domain.UserDepartmentBO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public Optional<String> getUserByUserId(String id);

    public boolean createUser(UserBO userBO);

    public List<UserBO> getListByName(String name);

    public List<UserDepartmentBO> getUserDepartmentListByName(String name);
}
