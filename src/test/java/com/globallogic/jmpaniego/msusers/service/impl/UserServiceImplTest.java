package com.globallogic.jmpaniego.msusers.service.impl;

import com.globallogic.jmpaniego.msusers.error.exception.UserException;
import com.globallogic.jmpaniego.msusers.mapper.PhoneMapper;
import com.globallogic.jmpaniego.msusers.model.User;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO;
import com.globallogic.jmpaniego.msusers.repository.UserRepository;
import com.globallogic.jmpaniego.msusers.utility.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Mock
    Util util;
    @Mock
    PhoneMapper phoneMapper;

    @Test
    void userAlreadyExists(){
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setEmail("1234@gmail.com");
        when(userRepository.findByEmail(signUpRequestDTO.getEmail())).thenThrow(UserException.class);

        assertThrows(UserException.class, ()-> userService.signup(signUpRequestDTO));
    }

    @Test
    void userSignUpSuccess(){
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
        signUpRequestDTO.setEmail("1234@gmail.com");
        signUpRequestDTO.setPhones(new ArrayList<>());
        User user = new User();
        user.setEmail("1234@gmail.com");
        user.setId(UUID.randomUUID());
        user.setPhones(new ArrayList<>());
        user.setCreatedAt(LocalDate.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        when(userRepository.findByEmail(signUpRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);
        when(util.generateToken(any())).thenReturn("");


        assertNotNull(userService.signup(signUpRequestDTO));
    }

}