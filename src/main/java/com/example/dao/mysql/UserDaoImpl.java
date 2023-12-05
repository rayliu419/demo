package com.example.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.dao.UserDao;
import com.example.dao.mysql.mapper.UserMapper;
import com.example.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

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
@Slf4j
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
//        return getListByNameBySQL(name);
        // 3. 分页+过滤结果
        return getResultByPage(name);
    }

    private List<UserEntity> getListByNameBySQL(String name) {
        return userMapper.getListByNameSQL(name);
    }

    /**
     * 分页加过滤显示
     * @param name
     * @return
     */
    public List<UserEntity> getResultByPage(String name) {
        // current从1开始
        // 分页的逻辑有点奇怪，跟想象的有点不一样
        Page page = new Page<>(1, 2);
        // 排序
//        page.addOrder(OrderItem.asc("name"));
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery();
        // 过滤
        queryWrapper.eq(UserEntity::getName, name);
        Page<UserEntity> entityPage = userMapper.selectPage(page, queryWrapper);
        log.info("total result -------------> {}", entityPage.getTotal());
        log.info("current page -------------> {}", entityPage.getCurrent());
        log.info("current size -------------> {}", entityPage.getSize());
        return entityPage.getRecords();
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
