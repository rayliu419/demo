-- 创建元数据的SQL语句

drop table user;
drop table department;

-- 创建department表
create table department (
   id int AUTO_INCREMENT primary key not null,
   name varchar(128) null
);

-- 创建user表
create table user (
   id varchar(128) primary key not null,
   name varchar(128) null,
   department_id int,
   CONSTRAINT fk_department_id FOREIGN KEY (department_id) REFERENCES department (id)
);

