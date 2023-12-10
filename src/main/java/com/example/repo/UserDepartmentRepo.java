package com.example.repo;

import com.example.dto.repo.UserDepartmentDTO;

import java.util.List;

public interface UserDepartmentRepo {

    List<UserDepartmentDTO> getUserDepartmentByName(String name);
}
