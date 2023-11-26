package com.example.dto.converter;

import com.example.dto.domain.DepartmentBO;
import com.example.dto.domain.UserBO;
import com.example.dto.request.CreateDepartmentRequestDTO;
import com.example.dto.request.CreateUserRequestDTO;

public class RequestDTOConverter {

    public static UserBO fromCreateUserRequest(CreateUserRequestDTO createUserRequestDTO) {
        UserBO userBO = new UserBO();
        userBO.setName(createUserRequestDTO.getName());
        return userBO;
    }

    public static DepartmentBO fromCreateDepartmentRequest(CreateDepartmentRequestDTO createDepartmentRequestDTO) {
        DepartmentBO departmentBO = new DepartmentBO();
        departmentBO.setName(createDepartmentRequestDTO.getName());
        return departmentBO;
    }
}
