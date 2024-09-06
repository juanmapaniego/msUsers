package com.globallogic.jmpaniego.msusers;


import com.globallogic.jmpaniego.msusers.controller.UserController;
import com.globallogic.jmpaniego.msusers.model.Phone;
import com.globallogic.jmpaniego.msusers.model.User;
import com.globallogic.jmpaniego.msusers.model.dto.SignUpRequestDTO;
import com.globallogic.jmpaniego.msusers.repository.UserRepository;
import com.globallogic.jmpaniego.msusers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaAuditing
public class Application implements CommandLineRunner {

	@Autowired
	public UserController userController;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SignUpRequestDTO signUpRequestDTO= new SignUpRequestDTO();
		//userController.signup(signUpRequestDTO);
	}
}
