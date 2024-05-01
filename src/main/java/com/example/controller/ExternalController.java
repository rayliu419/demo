package com.example.controller;

import com.example.dto.response.ExternalGetResponseDTO;
import com.example.dto.request.ExternalPostRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("external")
@Slf4j
public class ExternalController {

    @PostMapping("/post")
    public void post(ExternalPostRequestDTO externalPostRequestDTO) {

    }


    @GetMapping("/get")
    public ExternalGetResponseDTO get() {
        ExternalGetResponseDTO responseDTO = new ExternalGetResponseDTO();
        return responseDTO;
    }
}
