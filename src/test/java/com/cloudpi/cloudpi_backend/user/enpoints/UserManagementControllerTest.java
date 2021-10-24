package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.utils.UserEntityBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserManagementControllerTest {

    @Autowired
    private MockMvc mock;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser
    //TODO poprawić
    public void should_return_all_users() throws Exception {
        var result = mock.perform(
                get("/user-management/get-all")
        ).andExpect(
                status().is2xxSuccessful()
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        var response = result.getResponse();
        var rawBody = response.getContentAsString();

        ObjectMapper mapper = new JsonMapper();

        var body = mapper.readValue(rawBody, GetUserResponse[].class);

        assert body.length == 1;
    }

    @Test
    void should_save_new_user() {
        userRepository.save(UserEntityBuilder.aBobUser().build());
        var entities = userRepository.findAll();
        for(var entity : entities) {
            System.out.println(entity.getUsername());
        }
    }

    private void saveUsers() {

    }

}