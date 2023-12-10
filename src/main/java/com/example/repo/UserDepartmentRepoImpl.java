package com.example.repo;

import com.example.dto.repo.UserDepartmentDTO;
import com.example.repo.mysql.UserDepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "db.type", havingValue = "mysql")
@Slf4j
public class UserDepartmentRepoImpl implements UserDepartmentRepo {

    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Override
    public List<UserDepartmentDTO> getUserDepartmentByName(String name) {
        return userDepartmentMapper.getUserDepartmentByName(name);
    }
}
