package com.example.dao.mysql.repository;

import com.example.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    /**
     * 假设有个属性叫name, JPA可以自动生产SQL，findByXXX()。
     * MyBatis需要自己写Query。
     * @param name
     * @return
     */
    List<DepartmentEntity> findByName(String name);
}
