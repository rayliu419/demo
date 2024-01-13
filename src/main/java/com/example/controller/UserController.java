package com.example.controller;

import com.example.dto.converter.RequestDTOConverterMapper;
import com.example.dto.converter.VOConverterMapper;
import com.example.dto.domain.UserBO;
import com.example.dto.request.CreateUserRequestDTO;
import com.example.dto.response.GetAllByNameResponseDTO;
import com.example.dto.response.GetUserByNameResponseDTO;
import com.example.dto.vo.UserDepartmentVO;
import com.example.dto.vo.UserVO;
import com.example.service.UserService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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

    @Autowired
    MeterRegistry meterRegistry;

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
        incGetUserByNameCount(name);
        return getUserByNameResponseDTO;
    }

    @GetMapping("/getAll")
    @ResponseBody
    public GetAllByNameResponseDTO getAllByName(@RequestParam(value = "name") String name) {
        GetAllByNameResponseDTO getAllByNameResponseDTO = new GetAllByNameResponseDTO();
        List<UserDepartmentVO> userDepartmentVOList = userService.getUserDepartmentListByName(name).stream()
                .map(VOConverterMapper.INSTANCE::fromUserDepartmentBOToUserDepartmentVO)
                .toList();
        getAllByNameResponseDTO.setUserDepartmentVOList(userDepartmentVOList);
        return getAllByNameResponseDTO;
    }

    /**
     * 演示Counter变量打印出来的metrics
     * output:
     *    get.user.by.name{name=ivy} throughput=0.016667/s
     * 看起来跟想象的不一样，不一样打印 1 才对吗？为什么会有throughput?
     * @param name
     */
    private void incGetUserByNameCount(String name) {
        Counter counter = Counter
                .builder("get.user.by.name")
                .description("indicates get user by name api call count")
                .tags("name", name)
                .register(meterRegistry);
        counter.increment(1.0);
    }

}
