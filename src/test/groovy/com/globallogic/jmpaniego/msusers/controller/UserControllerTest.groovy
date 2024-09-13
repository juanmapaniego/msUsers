package com.globallogic.jmpaniego.msusers.controller

import com.globallogic.jmpaniego.msusers.exception.InvalidTokenException
import com.globallogic.jmpaniego.msusers.exception.UserException
import com.globallogic.jmpaniego.msusers.model.dto.LoginResponseDTO
import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO
import com.globallogic.jmpaniego.msusers.model.dto.SignUpResponseDTO
import com.globallogic.jmpaniego.msusers.service.UserService
import org.springframework.http.HttpStatus
import spock.lang.Specification



class UserControllerTest extends Specification {
    UserController userController;
    UserService userService;

    def setup(){
        userService = Mock(UserService)
        userController = new UserController(userService)
    }

    def "Signup ok"(){
        given:
            def signupresponse = Mock(SignUpResponseDTO)
            userService.signup(_ as SignUpRequestDTO) >> signupresponse
        when:
            def res = userController.signup(new SignUpRequestDTO())
        then:
            res != null
            res.getStatusCode() == HttpStatus.CREATED
    }

    def "Signup Thrown UserExists Exception"(){
        given: 'an exception is thrown by the service'
            def e = new UserException()
            userService.signup(_ as SignUpRequestDTO) >> {throw new UserException(_ as String)}
        when:
            def response = userController.signup(new SignUpRequestDTO())
        then:
            thrown(UserException)
            response == null
    }

    def "Login Throw InvalidToken Exception"(){
        given:
            userService.login(_ as String) >> {throw new InvalidTokenException(_ as String)}
        when:
            userController.login("")
        then:
            thrown(InvalidTokenException)
    }

    def "Login OK"(){
        given:
            def name = "test"
            def loginResponse = new LoginResponseDTO();
            loginResponse.setName(name)
            userService.login(_ as String) >> loginResponse
        when:
            def response = userController.login("")
        then:
            response != null
            response.getStatusCode() == HttpStatus.OK
            response.getBody().getName() == name
    }

}
