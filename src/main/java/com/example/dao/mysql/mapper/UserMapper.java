package com.example.dao.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * BaseMapper<T>是mybatisplus提供的，有许多方法可以直接使用，请直接查询BaseMapper，对于比较复杂的，则可以自己定义SQL语句来查询。
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    @Select("SELECT * FROM user WHERE name = #{name}")
    List<UserEntity> getListByNameSQL(@Param("name") String name);
}
