package com.globallogic.jmpaniego.msusers.controller;

import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpResponseDTO;
import com.globallogic.jmpaniego.msusers.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDTO> signup(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO){
        SignUpResponseDTO signup = userService.signup(signUpRequestDTO);
        return new ResponseEntity<>(signup, HttpStatus.CREATED);
    }

}
