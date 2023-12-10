package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.dto.converter.BOConverterMapper;
import com.example.dto.domain.UserBO;
import com.example.dto.domain.UserDepartmentBO;
import com.example.dto.repo.UserDepartmentDTO;
import com.example.entity.UserEntity;
import com.example.repo.UserDepartmentRepo;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDepartmentRepo userDepartmentRepo;

    @Override
    public Optional<String> getUserByUserId(String id) {
        return userDao.getUserByUserId(id);
    }

    @Override
    public boolean createUser(UserBO userBO) {
        log.info("In Service, createUser");
        return userDao.createUser(BOConverterMapper.INSTANCE.fromUserBOToUserEntity(userBO));
    }

    @Override
    public List<UserBO> getListByName(String name) {
        List<UserEntity> users = userDao.getListByName(name);
        return users.stream()
                .map(BOConverterMapper.INSTANCE::fromUserEntityToUserBO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDepartmentBO> getUserDepartmentListByName(String name) {
        List<UserDepartmentDTO> userDepartmentDTOS = userDepartmentRepo.getUserDepartmentByName(name);
        return userDepartmentDTOS.stream()
                .map(BOConverterMapper.INSTANCE::fromUserDepartmentDTOToUserDepartmentBO)
                .collect(Collectors.toList());
    }
}
