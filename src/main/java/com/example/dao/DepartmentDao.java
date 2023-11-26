package com.example.dao;

import com.example.entity.DepartmentEntity;

import java.util.List;

public interface DepartmentDao {

    boolean createDepartment(DepartmentEntity departmentEntity);

    List<DepartmentEntity> getListByName(String name);
}
