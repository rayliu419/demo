package com.example.controller;

import com.example.dto.converter.RequestDTOConverterMapper;
import com.example.dto.converter.VOConverterMapper;
import com.example.dto.domain.DepartmentBO;
import com.example.dto.request.CreateDepartmentRequestDTO;
import com.example.dto.response.GetDepartmentByNameResponseDTO;
import com.example.dto.vo.DepartmentVO;
import com.example.service.DepartmentService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
@Slf4j
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/create")
    public String create(@RequestBody CreateDepartmentRequestDTO createDepartmentRequestDTO) {
        log.info("In DepartmentController create");
        DepartmentBO bo = RequestDTOConverterMapper.INSTANCE
                .fromCreateDepartmentRequestToDepartmentBO(createDepartmentRequestDTO);
        departmentService.createDepartment(bo);
        return "create department";
    }

    @GetMapping("/get")
    @ResponseBody
    public GetDepartmentByNameResponseDTO getByName(@RequestParam(value = "name") String name) {
        GetDepartmentByNameResponseDTO dto = new GetDepartmentByNameResponseDTO();
        List<DepartmentVO> departmentVOS = departmentService.getListByName(name).stream()
                .map(VOConverterMapper.INSTANCE::fromDepartmentBOToDepartmentVO)
                .toList();
        dto.setDepartmentVOS(departmentVOS);
        return dto;
    }

}
