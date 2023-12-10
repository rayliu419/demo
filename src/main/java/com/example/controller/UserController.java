package com.example.controller;

import com.example.dto.converter.RequestDTOConverterMapper;
import com.example.dto.converter.VOConverterMapper;
import com.example.dto.domain.UserBO;
import com.example.dto.request.CreateUserRequestDTO;
import com.example.dto.response.GetUserByNameResponseDTO;
import com.example.dto.vo.UserVO;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public String create(@RequestBody CreateUserRequestDTO createUserRequest) {
        log.info("In UserController create");
        UserBO userBO = RequestDTOConverterMapper.INSTANCE.fromCreateUserRequestToUserBO(createUserRequest);
        userService.createUser(userBO);
        return "create user";
    }

    @GetMapping("/get")
    @ResponseBody
    public GetUserByNameResponseDTO getByName(@RequestParam(value = "name") String name) {
        GetUserByNameResponseDTO getUserByNameResponseDTO = new GetUserByNameResponseDTO();
        List<UserVO> userVOList = userService.getListByName(name).stream()
                .map(VOConverterMapper.INSTANCE::fromUserBOToUserVO)
                .toList();
        getUserByNameResponseDTO.setUserVOList(userVOList);
        return getUserByNameResponseDTO;
    }

}
