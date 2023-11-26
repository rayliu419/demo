package com.example.service.impl;

import com.example.dao.UserDao;
import com.example.dto.converter.BOConverter;
import com.example.dto.domain.UserBO;
import com.example.entity.UserEntity;
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

    @Override
    public Optional<String> getUserByUserId(String id) {
        return userDao.getUserByUserId(id);
    }

    @Override
    public boolean createUser(UserBO userBO) {
        log.info("In Service, createUser");
        return userDao.createUser(BOConverter.fromUserBOToUserEntity(userBO));
    }

    @Override
    public List<UserBO> getListByName(String name) {
        List<UserEntity> users = userDao.getListByName(name);
        return users.stream()
                .map(BOConverter::fromUserEntityToUserBO)
                .collect(Collectors.toList());
    }
}
