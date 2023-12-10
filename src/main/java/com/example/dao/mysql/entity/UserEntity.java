package com.example.dao.mysql.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")  // MyBatis的注解，代表是数据表
public class UserEntity {

    @TableId("id")  // MyBatis的注解，代表是字段
    String id;

    private String name;

    /**
     * 外键。
     * 发现一个问题：
     * 在没有加入  @TableField("value = department_id")时，getListByName老是出现department_id为0的情况。(departmentId以前是
     * department_id)，原因如下：
     * 在实体类与数据库表之间，字段名没有正确对应。默认情况下，MyBatis-Plus 会使用[驼峰命名规则自动匹配字段名]，
     * 但如果数据库表中的字段名与实体类字段名不匹配，需要使用@TableField 注解中的value属性指定映射的数据库表字段名。
     */
    /**
     * 第二个情况是@TableField("value = department_id") != @TableField("department_id")
     * 为什么改完以后@TableField("value = department_id")说是SQL语句有问题，但是改成@TableField("department_id")就可以了？
     * 会变成:
     * SQL: INSERT INTO user  ( id, name, value = department_id )  VALUES  ( ?, ?, ? )
     * 最为奇怪的是，以前是可以工作的。
     *
     */
    @TableField("department_id")
    private int departmentId;
}



