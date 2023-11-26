package com.example.dao.mysql;

import com.example.dao.DepartmentDao;
import com.example.dao.mysql.repository.DepartmentRepository;
import com.example.entity.DepartmentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(name = "db.type", havingValue = "mysql")
@Slf4j
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public boolean createDepartment(DepartmentEntity departmentEntity) {
        DepartmentEntity entity = departmentRepository.save(departmentEntity);
        return true;
    }

    @Override
    public List<DepartmentEntity> getListByName(String name) {
        return departmentRepository.findByName(name);
    }
}
