package com.example.service.impl;

import com.example.dao.DepartmentDao;
import com.example.dto.converter.BOConverter;
import com.example.dto.converter.VOConverter;
import com.example.dto.domain.DepartmentBO;
import com.example.entity.DepartmentEntity;
import com.example.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public Optional<String> getDepartmentById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean createDepartment(DepartmentBO bo) {
        log.info("In Service, createDepartment");
        DepartmentEntity entity = BOConverter.fromDepartmentBOToDepartmentEntity(bo);
        return departmentDao.createDepartment(entity);
    }

    @Override
    public List<DepartmentBO> getListByName(String name) {
        List<DepartmentEntity> entities = departmentDao.getListByName(name);
        return entities.stream()
                .map(BOConverter::fromDepartmentEntityToDepartmentBO)
                .collect(Collectors.toList());
    }
}
