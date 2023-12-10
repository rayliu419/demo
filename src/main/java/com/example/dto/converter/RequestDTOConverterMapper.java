package com.example.dto.converter;

import com.example.dto.domain.DepartmentBO;
import com.example.dto.domain.UserBO;
import com.example.dto.request.CreateDepartmentRequestDTO;
import com.example.dto.request.CreateUserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestDTOConverterMapper {

    RequestDTOConverterMapper INSTANCE = Mappers.getMapper(RequestDTOConverterMapper.class);

    UserBO fromCreateUserRequestToUserBO(CreateUserRequestDTO createUserRequestDTO);

    DepartmentBO fromCreateDepartmentRequestToDepartmentBO(CreateDepartmentRequestDTO createDepartmentRequestDTO);
}
