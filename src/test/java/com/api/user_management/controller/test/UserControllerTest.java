package com.api.user_management.controller.test;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.user_management.controller.UserControllers;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.service.Impl.UserServiceImpl;
import com.api.user_management.shared.dto.UserDto;

@WebMvcTest(UserControllers.class)

public class UserControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    UserRepository userRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
//    @DataJpaTest
//    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    private UserControllers userController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        
    }

    @Test
    @Order(1)
    void testGetAllUsers() throws Exception {
        when(userService.getUsers(0, 0, null)).thenReturn(Arrays.asList(new 
        		UserDto(1L,"aLlKTLumEcnUj7y0oR3kkMolm7Yw6Z", "John","Gidey","Tamrat","+390909090909","https://springframework.guru", "john@example.com","$2a$10$zmVPLp2hbG8EUHhXEXRA5O2CJnnfrBG8cAMLzZX.2IdZhXSBP/nQC","8909","Admin","userStatus", null, null, null, null, null)));

//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name").value("John Doe"));
//
//        verify(userService, times(1)).getAllUsers();
        
        //Action
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        //Verify
        System.out.println(userEntities);
        Assertions.assertThat(userEntities.size()).isGreaterThan(0);
    }
//
    @Test
    @Order(2)
    void testGetUserById() throws Exception {
        //Action
        UserEntity userEntities = userRepository.findById(1L).get();
        //Verify
        System.out.println(userEntities);
        Assertions.assertThat(userEntities.getId()).isEqualTo(1L);
    }
//
    @Test
    @Order(3)
    void testCreateUser() throws Exception {
        //Action
        UserEntity userEntity = new UserEntity(1L,"aLlKTLumEcnUj7y0oR3kkMolm7Yw6Z", "John","Gidey","Tamrat",
        		"+390909090909","https://springframework.guru", "john@example.com",
        		"$2a$10$zmVPLp2hbG8EUHhXEXRA5O2CJnnfrBG8cAMLzZX.2IdZhXSBP/nQC","8909","Admin","userStatus",
        		null, null, null, null, null);
        userRepository.save(userEntity);

        //Verify
        System.out.println(userEntity);
        Assertions.assertThat(userEntity.getId()).isGreaterThan(0);
    }
//
    @Test
    @Order(4)
    @Rollback(value = false)
    void testUpdateUser() throws Exception {

        //Action
        UserEntity userEntity = userRepository.findById(1L).get();
        userEntity.setEmail("samcurran@gmail.com");
        userEntity.setFirstName("Brhane");
        userEntity.setLastName("Gidey");
        userEntity.setMiddleName("Tamrat");
        userEntity.setPhoneNumber("+393512558241");
        userEntity.setEncryptedPassword("12345678");
        userEntity.setUserType("Admin");
        
        UserEntity userUpdated =  userRepository.save(userEntity);

        //Verify
        System.out.println(userUpdated);
        Assertions.assertThat(userUpdated.getEmail()).isEqualTo("samcurran@gmail.com");
    }
//
    @Test
    void testDeleteUser() throws Exception {
        //Action
        userRepository.deleteById(1L);
        Optional<UserEntity> employeeOptional = userRepository.findById(1L);

        //Verify
        Assertions.assertThat(employeeOptional).isEmpty();
    }
//}
//
//    }

}
