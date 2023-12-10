package com.example.repo.mysql;

import com.example.dto.repo.UserDepartmentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 没有实际的数据表实体对应的mapper写法。
 */
@Mapper
public interface UserDepartmentMapper {

    @Select("SELECT u.id, u.name, d.id as departmentId, d.name as departmentName " +
            "FROM user u LEFT JOIN department d " +
            "ON u.department_id = d.id " +
            "WHERE u.name = #{name}")
    List<UserDepartmentDTO> getUserDepartmentByName(@Param("name") String name);
}
