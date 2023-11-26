package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")  // MyBatis的注解，代表是数据表
public class UserEntity {

    @TableId("id")  // MyBatis的注解，代表是字段
    String id;

    private String name;

}

// 创建表命令
//  create table user (
//       id varchar(128) primary key not null,
//       name varchar(128) null
//   )

