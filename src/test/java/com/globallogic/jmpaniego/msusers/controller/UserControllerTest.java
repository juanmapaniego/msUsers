package com.globallogic.jmpaniego.msusers.controller;

import com.globallogic.jmpaniego.msusers.error.exception.InvalidTokenException;
import com.globallogic.jmpaniego.msusers.error.exception.UserException;
import com.globallogic.jmpaniego.msusers.model.dto.LoginResponseDTO;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpResponseDTO;
import com.globallogic.jmpaniego.msusers.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    void signupThrownUserExists(){
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        when(userService.signup(any())).thenThrow(UserException.class);
        assertThrows(UserException.class, ()-> userController.signup(signUpRequestDTO));
    }

    @Test
    void signupSuccess(){
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        when(userService.signup(any())).thenReturn(new SignUpResponseDTO());
        assertNotNull(userController.signup(signUpRequestDTO));
    }

    @Test
    void loginThrownInvalidToken(){
        when(userService.login(any())).thenThrow(InvalidTokenException.class);
        assertThrows(InvalidTokenException.class, ()->userController.login("test"));
    }

    @Test
    void loginSuccess(){
        when(userService.login(any())).thenReturn(new LoginResponseDTO());
        assertNotNull(userController.login("test"));
    }
}