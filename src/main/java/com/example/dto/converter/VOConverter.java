package com.example.dto.converter;

import com.example.dto.domain.UserBO;
import com.example.dto.vo.UserVO;

public class VOConverter {

    public static UserVO fromUserBOToUserVO(UserBO userBO) {
        UserVO userVO = new UserVO();
        userVO.setName(userBO.getName());
        return userVO;
    }
}
