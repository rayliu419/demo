package com.example.service.impl;

import com.example.dao.DepartmentDao;
import com.example.dto.converter.BOConverterMapper;
import com.example.dto.domain.DepartmentBO;
import com.example.dao.mysql.entity.DepartmentEntity;
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
    public boolean createDepartment(DepartmentBO departmentBO) {
        log.info("In Service, createDepartment");
        return departmentDao.createDepartment(departmentBO);
    }

    @Override
    public List<DepartmentBO> getListByName(String name) {
        return departmentDao.getListByName(name);
    }
}
