package com.example.dto.converter;

import com.example.dto.domain.DepartmentBO;
import com.example.dto.domain.UserBO;
import com.example.entity.DepartmentEntity;
import com.example.entity.UserEntity;

public class BOConverter {

    public static UserEntity fromUserBOToUserEntity(UserBO userBO) {
        UserEntity user = new UserEntity();
        user.setName(userBO.getName());
        return user;
    }

    public static UserBO fromUserEntityToUserBO(UserEntity user) {
        UserBO userBO = new UserBO();
        userBO.setName(user.getName());
        return userBO;
    }

    public static DepartmentEntity fromDepartmentBOToDepartmentEntity(DepartmentBO departmentBO) {
        DepartmentEntity entity = new DepartmentEntity();
        entity.setName(departmentBO.getName());
        return entity;
    }

    public static DepartmentBO fromDepartmentEntityToDepartmentBO(DepartmentEntity entity) {
        DepartmentBO bo  = new DepartmentBO();
        bo.setName(entity.getName());
        return bo;
    }
}
