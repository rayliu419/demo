package com.example.dto.response;

import com.example.dto.vo.DepartmentVO;
import com.example.dto.vo.UserVO;
import lombok.Data;

import java.util.List;

@Data
public class GetDepartmentByNameResponseDTO {

    List<DepartmentVO> departmentVOS;
}
