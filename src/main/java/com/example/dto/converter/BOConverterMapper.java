package com.example.dto.converter;

import com.example.dto.domain.DepartmentBO;
import com.example.dto.domain.UserBO;
import com.example.dto.domain.UserDepartmentBO;
import com.example.dto.repo.UserDepartmentDTO;
import com.example.entity.DepartmentEntity;
import com.example.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BOConverterMapper {

    BOConverterMapper INSTANCE = Mappers.getMapper(BOConverterMapper.class);

    UserEntity fromUserBOToUserEntity(UserBO userBO);

//    @Mapping(target = "id", ignore = true)
    // 如果原数据源没有字段，会自动忽略，所以上面是多余的。
    UserBO fromUserEntityToUserBO(UserEntity user);

    DepartmentEntity fromDepartmentBOToDepartmentEntity(DepartmentBO departmentBO);

    DepartmentBO fromDepartmentEntityToDepartmentBO(DepartmentEntity entity);

    UserDepartmentBO fromUserDepartmentDTOToUserDepartmentBO(UserDepartmentDTO dto);
}
