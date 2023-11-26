package com.example.dto.converter;

import com.example.dto.domain.DepartmentBO;
import com.example.dto.domain.UserBO;
import com.example.dto.vo.DepartmentVO;
import com.example.dto.vo.UserVO;

public class VOConverter {

    public static UserVO fromUserBOToUserVO(UserBO userBO) {
        UserVO userVO = new UserVO();
        userVO.setName(userBO.getName());
        return userVO;
    }

    public static DepartmentVO fromDepartmentBOToDepartmentVO(DepartmentBO bo) {
        DepartmentVO departmentVO = new DepartmentVO();
        departmentVO.setName(bo.getName());
        return departmentVO;
    }
}
