package com.globallogic.jmpaniego.msusers.repository;

import com.globallogic.jmpaniego.msusers.model.Phone;
import com.globallogic.jmpaniego.msusers.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private UUID id;
    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        User userTest = User.builder()
                .id(id)
                .name("Test")
                .email("123@gmail.com")
                .password("123")
                .phones(Arrays.asList(Phone.builder().number(123L).cityCode(1).countryCode("+54").build()))
                .build();
        User userTest2 = User.builder()
                .id(UUID.randomUUID())
                .name("Test2")
                .email("12@gmail.com")
                .password("123")
                .phones(Arrays.asList(Phone.builder().number(456L).cityCode(2).countryCode("+54").build()))
                .build();
        userRepository.save(userTest);
        userRepository.save(userTest2);
    }

    @Test
    void getList() {
        assertEquals(2,userRepository.findAll().size());
    }

    @Test
    void getByIdTest(){
        assertNotNull(userRepository.findById(id).orElse(null));
    }

    @Test
    void emailExists(){
        String emailExist ="12@gmail.com";
        assertNotNull(userRepository.findByEmail(emailExist).get());
    }

    @Test
    void emailNotExists(){
        String emailNotExist ="1234@gmail.com";
        assertNull(userRepository.findByEmail(emailNotExist).orElse(null));
    }
}