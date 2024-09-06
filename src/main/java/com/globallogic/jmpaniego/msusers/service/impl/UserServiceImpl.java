package com.globallogic.jmpaniego.msusers.service.impl;

import com.globallogic.jmpaniego.msusers.error.exception.InvalidTokenException;
import com.globallogic.jmpaniego.msusers.error.exception.UserException;
import com.globallogic.jmpaniego.msusers.mapper.PhoneMapper;
import com.globallogic.jmpaniego.msusers.model.User;
import com.globallogic.jmpaniego.msusers.model.dto.LoginResponseDTO;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpResponseDTO;
import com.globallogic.jmpaniego.msusers.repository.UserRepository;
import com.globallogic.jmpaniego.msusers.service.UserService;
import com.globallogic.jmpaniego.msusers.utility.Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
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

    @Override
    public LoginResponseDTO login(String jwt) {
        if(Objects.isNull(jwt) || jwt.isEmpty())
            throw new InvalidTokenException("Token not sended");
        if(!jwt.startsWith("Bearer "))
            throw new InvalidTokenException("The token must be of bearer type");
        jwt = jwt.substring(7);
        util.validateToken(jwt);
        String email = util.getEmail(jwt);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));
        return LoginResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isActive(user.getActive())
                .phones(user.getPhones().stream().map(phoneMapper::phoneToPhoneDto).collect(Collectors.toList()))
                .password(user.getPassword()) //se retorna la password encriptada con bcrypt
                .created(user.getCreatedAt())
                .lastLogin(LocalDateTime.now())
                .token(util.generateToken(user))
                .build();
    }
}
