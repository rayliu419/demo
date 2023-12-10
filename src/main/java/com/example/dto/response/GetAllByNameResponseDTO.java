package com.example.dto.response;

import com.example.dto.vo.UserDepartmentVO;
import lombok.Data;

import java.util.List;

@Data
public class GetAllByNameResponseDTO {

    List<UserDepartmentVO> userDepartmentVOList;
}
