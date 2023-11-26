package com.example.controller;

import com.example.dto.converter.RequestDTOConverter;
import com.example.dto.converter.VOConverter;
import com.example.dto.domain.DepartmentBO;
import com.example.dto.request.CreateDepartmentRequestDTO;
import com.example.dto.response.GetDepartmentByNameResponseDTO;
import com.example.dto.vo.DepartmentVO;
import com.example.service.DepartmentService;
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
        DepartmentBO bo = RequestDTOConverter.fromCreateDepartmentRequest(createDepartmentRequestDTO);
        departmentService.createDepartment(bo);
        return "create user";
    }

    @GetMapping("/get")
    @ResponseBody
    public GetDepartmentByNameResponseDTO getByName(@RequestParam(value = "name") String name) {
        GetDepartmentByNameResponseDTO dto = new GetDepartmentByNameResponseDTO();
        List<DepartmentVO> departmentVOS = departmentService.getListByName(name).stream()
                .map(VOConverter::fromDepartmentBOToDepartmentVO)
                .toList();
        dto.setDepartmentVOS(departmentVOS);
        return dto;
    }

}
