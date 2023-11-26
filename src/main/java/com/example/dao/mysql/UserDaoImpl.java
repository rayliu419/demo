package com.example.dao.mysql;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.dao.UserDao;
import com.example.dao.mysql.mapper.UserMapper;
import com.example.entity.UserEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * docker 启动:
 * docker run -itd --name mysql-demo -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql
 * 进入容器:
 * docker exec -it 858fa48aed57 bash
 * 运行命令行:
 * mysql -uroot -p123456
 * 命令行内：
 * create database demo; // 建立databse
 * use demo; // 改换database
 * create table ... // 参考src/main/java/com/example/entity/User.java
 * desc user;
 * 关掉进程：
 * docker stop {mysql_container_id}
 * 后续运行：
 * docker start mysql-demo
 *
 */
@Component
@ConditionalOnProperty(name = "db.type", havingValue = "mysql")
@Log4j2
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean createUser(UserEntity user) {
        user.setId(String.valueOf(UUID.randomUUID()));
        log.info("userMapper create user - {}", user.toString());
        userMapper.insert(user);
        return true;
    }

    @Override
    public Optional<String> getUserByUserId(String id) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> getListByName(String name) {
        // 1. 使用LambdaQueryWrapper 查询
//        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
//        queryWrapper.eq(UserEntity::getName, name);
//        // BaseMapper在没有结果的时候，返回empty List，不是null
//        return userMapper.selectList(queryWrapper);
        // 2. 使用自定义的SQL查询
        return getListByNameBySQL(name);
    }

    private List<UserEntity> getListByNameBySQL(String name) {
        return userMapper.getListByNameSQL(name);
    }

    @Override
    public boolean updateUser(UserEntity user) {
        return false;
    }

    @Override
    public boolean deleteUserByUserId(String id) {
        return false;
    }
}
