package com.example.dao.impl;

import com.example.dao.UserDao;
import com.example.entity.Address;
import com.example.entity.User;

import java.util.Optional;

public class UserDaoImpl implements UserDao {

    /**
     * Demonstrate the usage of Optional.
     * Don't worry about the NPE in the middle.
     * Upstream needs to call as getDistrictByUserId(id).ifPresent xxx
     *
     * @param id
     * @return
     */
    public Optional<String> getDistrictByUserId(String id) {
        User user = new User();
        return Optional.of(user).map(User::getAddress).map(Address::getDistrict);
    }

}
