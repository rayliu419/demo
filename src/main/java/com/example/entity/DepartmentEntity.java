package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "department")  // Spring JPA的注解，用来定义实体类
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

// 创建表命令
//  create table department (
//       id int AUTO_INCREMENT primary key not null,
//       name varchar(128) null
//   )
