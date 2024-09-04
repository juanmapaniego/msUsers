package com.globallogic.jmpaniego.msusers;


import com.globallogic.jmpaniego.msusers.model.Phone;
import com.globallogic.jmpaniego.msusers.model.User;
import com.globallogic.jmpaniego.msusers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class Application  {

	@Autowired
	public UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
