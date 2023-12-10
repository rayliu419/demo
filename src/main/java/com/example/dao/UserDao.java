package com.example.dao;

import com.example.dao.mysql.entity.UserEntity;
import com.example.dto.domain.UserBO;

import java.util.List;
import java.util.Optional;

/**
 * Dao层的输入输出都不应该是Entity类型的，考虑Dao使用不同的数据存储层，例如User可以用mysql或者redis。在使用redis的时候，实际上底层的数据模型
 * 是不一样的，redis层是key-value形式的，由于UserDao的接口是被Service层调用。如果底层直接限制死UserEntity，则redis不好设配。
 * UserEntity应该只是mysql层的数据模型。
 *
 * 所以Dao层的输入输出层都应该是domain的BO层!!
 *
 */
public interface UserDao {

    boolean createUser(UserBO user);

    Optional<String> getUserByUserId(String id);

    List<UserBO> getListByName(String name);

    boolean updateUser(UserEntity user);

    boolean deleteUserByUserId(String id);
}
