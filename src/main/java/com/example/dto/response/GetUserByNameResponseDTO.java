package com.example.dto.response;

import com.example.dto.vo.UserVO;
import lombok.Data;

import java.util.List;

@Data
public class GetUserByNameResponseDTO {

    List<UserVO> users;
}
