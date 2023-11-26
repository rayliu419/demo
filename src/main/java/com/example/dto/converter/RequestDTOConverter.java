package com.example.dto.converter;

import com.example.dto.domain.UserBO;
import com.example.dto.request.CreateUserRequestDTO;

public class RequestDTOConverter {

    public static UserBO fromCreateUserRequest(CreateUserRequestDTO createUserRequestDTO) {
        UserBO userBO = new UserBO();
        userBO.setName(createUserRequestDTO.getName());
        return userBO;
    }
}
