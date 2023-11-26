package com.example.service;

import com.example.dto.domain.DepartmentBO;
import com.example.dto.domain.UserBO;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    public Optional<String> getDepartmentById(String id);

    public boolean createDepartment(DepartmentBO bo);

    public List<DepartmentBO> getListByName(String name);
}
