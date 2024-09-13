package com.globallogic.jmpaniego.msusers.service.impl

import com.globallogic.jmpaniego.msusers.exception.InvalidTokenException
import com.globallogic.jmpaniego.msusers.exception.UserException
import com.globallogic.jmpaniego.msusers.mapper.PhoneMapper
import com.globallogic.jmpaniego.msusers.model.Phone
import com.globallogic.jmpaniego.msusers.model.User
import com.globallogic.jmpaniego.msusers.model.dto.PhoneDTO
import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO
import com.globallogic.jmpaniego.msusers.repository.UserRepository
import com.globallogic.jmpaniego.msusers.utility.Util
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class UserServiceImplTest extends Specification{
    UserServiceImpl userService;
    UserRepository userRepository;
    Util util;
    PhoneMapper phoneMapper;

    def setup(){
        userRepository = Mock(UserRepository)
        util = Mock(Util)
        phoneMapper = Mock(PhoneMapper)
        userService = new UserServiceImpl(userRepository,util,phoneMapper)
    }

    def "User already exists"(){
        given:
            def signUpRequestDTO = new SignUpRequestDTO();
            signUpRequestDTO.setEmail("1234@gmail.com");
            userRepository.findByEmail(signUpRequestDTO.getEmail())  >> Optional.of(new User())
        when:
            userService.signup(signUpRequestDTO)
        then:
            thrown(UserException)
    }

    def "User signup OK"(){
        given:
            def signUpRequestDTO = new SignUpRequestDTO();
            signUpRequestDTO.setEmail("1234@gmail.com");
            signUpRequestDTO.setPhones(new ArrayList<>());
            def email = "1234@gmail.com"
            def user = new User();
            user.setEmail(email);
            user.setId(UUID.randomUUID());
            user.setPhones(new ArrayList<>());
            user.setCreatedAt(LocalDate.now());
            user.setLastLogin(LocalDateTime.now());
            user.setActive(true);
            userRepository.findByEmail(signUpRequestDTO.getEmail()) >> Optional.empty()
            userRepository.save(_ as User) >> user;
            util.generateToken(_ as User) >> ""
        when:
            def response = userService.signup(signUpRequestDTO)
        then:
            response != null
            response.getEmail() == email
    }

    def "User try to login but send a null token"(){
        when:
            userService.login(null)
        then:
            thrown(InvalidTokenException)
    }

    def "User send not a Bearer token"(){
        given:
            def token = "Bear test"
        when:
            userService.login(token)
        then:
            thrown(InvalidTokenException)
    }

    def "User try to login but send an invalid token"(){
        given:
            def token = "Bearer eytest"
            util.validateToken(_ as String) >> {throw new InvalidTokenException(_ as String)}
        when:
            userService.login(token)
        then:
            thrown(InvalidTokenException)
    }


    def "User login OK"(){
        given:
            def token = "Bearer eytest"
            def email = "1234@gmail.com"
            def user = new User();
            user.setPhones(new ArrayList<>());
            user.setEmail(email);
            util.getEmail(_ as String) >> email
            userRepository.findByEmail(_ as String) >> Optional.of(user)
            phoneMapper.phoneToPhoneDto(_ as Phone) >> new PhoneDTO()
        when:
            def response = userService.login(token)
        then:
            response != null
            1 * util.validateToken(_)
            response.getEmail() == email
            response.getPhones().size() == 0
    }


}