package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.cloudpi.cloudpi_backend.user.utils.UserEntityBuilder.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class UserManagementControllerTest {

    @Autowired
    private MockMvc mock;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser
    public void should_return_all_users() throws Exception {
        //given
        var inputEntities = saveUsers(
                aRootUser().build(),
                anAliceUser().build(),
                aBobUser().build()
        );

        //when
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
        var responseBody = mapper.readValue(rawBody, GetUserResponse[].class);

        //then
        assert responseBody.length == 3;
        for(var userInResponse : responseBody) {
            assert inputEntities.stream().anyMatch(userEntity ->
                            userEntity.getUsername().equals(userInResponse.getUsername()) &&
                            userEntity.getAccountType().equals(userInResponse.getAccountType())
            );

        }
    }

    private List<UserEntity> saveUsers(UserEntity... entities) {
        var listOfEntities = List.of(entities);
        userRepository.saveAll(listOfEntities);

        return listOfEntities;
    }

}