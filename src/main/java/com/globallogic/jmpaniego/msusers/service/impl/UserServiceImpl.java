package com.globallogic.jmpaniego.msusers.service.impl;

import com.globallogic.jmpaniego.msusers.error.exception.UserException;
import com.globallogic.jmpaniego.msusers.mapper.PhoneMapper;
import com.globallogic.jmpaniego.msusers.model.User;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpResponseDTO;
import com.globallogic.jmpaniego.msusers.repository.UserRepository;
import com.globallogic.jmpaniego.msusers.service.UserService;
import com.globallogic.jmpaniego.msusers.utility.Util;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Util util;
    private final PhoneMapper phoneMapper;

    public UserServiceImpl(UserRepository userRepository, Util util, PhoneMapper phoneMapper) {
        this.userRepository = userRepository;
        this.util = util;
        this.phoneMapper = phoneMapper;
    }

    @Override
    public SignUpResponseDTO signup(SignUpRequestDTO signUpRequestDTO) {
        Optional<User> userExist = userRepository.findByEmail(signUpRequestDTO.getEmail());
        if(userExist.isPresent()){
            throw new UserException("Email already exists");
        }
        signUpRequestDTO.setPassword(
                util.encodePassword(signUpRequestDTO.getPassword())
        );
        User user = new User(signUpRequestDTO);
        user = userRepository.save(user);
        return SignUpResponseDTO.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .email(user.getEmail())
                .token(util.generateToken(user))
                .phones(user.getPhones().stream().map(phoneMapper::phoneToPhoneDto).collect(Collectors.toList()))
                .created(user.getCreatedAt().toString())
                .isActive(user.getActive().toString())
                .lastLogin(user.getLastLogin().toString())
                .build();
    }
}
