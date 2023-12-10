package com.example.dao;

import com.example.dto.domain.DepartmentBO;

import java.util.List;

public interface DepartmentDao {

    boolean createDepartment(DepartmentBO departmentBO);

    List<DepartmentBO> getListByName(String name);
}
