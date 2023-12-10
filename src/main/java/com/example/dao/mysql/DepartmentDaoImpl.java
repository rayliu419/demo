package com.example.dao.mysql;

import com.example.dao.DepartmentDao;
import com.example.dao.mysql.repository.DepartmentRepository;
import com.example.dao.mysql.entity.DepartmentEntity;
import com.example.dto.converter.BOConverterMapper;
import com.example.dto.domain.DepartmentBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "db.type", havingValue = "mysql")
@Slf4j
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public boolean createDepartment(DepartmentBO departmentBO) {
        DepartmentEntity entity = BOConverterMapper.INSTANCE.fromDepartmentBOToDepartmentEntity(departmentBO);
        DepartmentEntity saved = departmentRepository.save(entity);
        return true;
    }

    @Override
    public List<DepartmentBO> getListByName(String name) {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findByName(name);
        return departmentEntityList.stream()
                .map(BOConverterMapper.INSTANCE::fromDepartmentEntityToDepartmentBO)
                .collect(Collectors.toList());
    }
}
