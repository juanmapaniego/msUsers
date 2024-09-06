package com.globallogic.jmpaniego.msusers.service;

import com.globallogic.jmpaniego.msusers.model.dto.LoginResponseDTO;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpResponseDTO;

public interface UserService {
    SignUpResponseDTO signup(SignUpRequestDTO signUpRequestDTO);

    LoginResponseDTO login(String jwt);
}
