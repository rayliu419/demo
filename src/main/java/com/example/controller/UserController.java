package com.example.controller;

import com.example.dto.converter.RequestDTOConverter;
import com.example.dto.converter.VOConverter;
import com.example.dto.domain.UserBO;
import com.example.dto.request.CreateUserRequestDTO;
import com.example.dto.response.GetUserByNameResponseDTO;
import com.example.dto.vo.UserVO;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public String create(@RequestBody CreateUserRequestDTO createUserRequest) {
        log.info("In UserController create");
        UserBO userBO = RequestDTOConverter.fromCreateUserRequest(createUserRequest);
        userService.createUser(userBO);
        return "create user";
    }

    @GetMapping("/get")
    @ResponseBody
    public GetUserByNameResponseDTO getByName(@RequestParam(value = "name") String name) {
        GetUserByNameResponseDTO getUserByNameResponseDTO = new GetUserByNameResponseDTO();
        List<UserVO> userVOList = userService.getListByName(name).stream()
                .map(VOConverter::fromUserBOToUserVO)
                .toList();
        getUserByNameResponseDTO.setUsers(userVOList);
        return getUserByNameResponseDTO;
    }

}
