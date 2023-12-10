package com.example.dto.converter;

import com.example.dto.domain.DepartmentBO;
import com.example.dto.domain.UserBO;
import com.example.dto.domain.UserDepartmentBO;
import com.example.dto.vo.DepartmentVO;
import com.example.dto.vo.UserDepartmentVO;
import com.example.dto.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VOConverterMapper {

    VOConverterMapper INSTANCE = Mappers.getMapper(VOConverterMapper.class);

    UserVO fromUserBOToUserVO(UserBO userBO);

    DepartmentVO fromDepartmentBOToDepartmentVO(DepartmentBO bo);

    UserDepartmentVO fromUserDepartmentBOToUserDepartmentVO(UserDepartmentBO bo);
}
