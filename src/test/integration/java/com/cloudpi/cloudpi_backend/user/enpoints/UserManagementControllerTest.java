package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.test.utils.CustomAssertions;
import com.cloudpi.cloudpi_backend.test.utils.ModelComparator;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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



    @Nested
    @DisplayName("/user-management/get-all")
    class GetALL {
        @Test
        @WithMockUser
        public void should_return_all_users() throws Exception {
            //given
            var inputEntities = saveDefaultUsers();

            //when
            var result = mock.perform(
                    get("/user-management/get-all")
            ).andExpect(
                    status().is(200)
            ).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON)
            ).andReturn();


            var responseBody = getBody(result, GetUserResponse[].class);

            //then
            assert responseBody.length == 3;
            for (var userInResponse : responseBody) {
                assert inputEntities.stream().anyMatch(userEntity ->
                        userEntity.getUsername().equals(userInResponse.getUsername()) &&
                                userEntity.getAccountType().equals(userInResponse.getAccountType())
                );

            }
        }

        @Test
        @WithMockUser
        public void should_return_empty_list() throws Exception {
            //given

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
            assert responseBody.length == 0;
        }

        @Test
        public void should_return_403_unauthorized() throws Exception {
            //given
            saveDefaultUsers();

            //when
            mock.perform(
                    get("/user-management/get-all")
            ).andExpect(
                    status().is(403)
            );
        }
    }



    @Nested
    @DisplayName("/user-management/get-all/with-details")
    class GetAllWithDetails {
        @Test
        @WithMockUser(authorities = {UserAPIAuthorities.GET_DETAILS})
        public void should_return_200_with_userWithDetails_list() throws Exception {
            //given
            var inputEntities = saveDefaultUsers();

            //when
            var result = mock.perform(
                    get("/user-management/get-all/with-details")
            ).andExpect(
                    status().is2xxSuccessful()
            ).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON)
            ).andReturn();

            var responseBody = getBody(result, GetUserWithDetailsResponse[].class);
            CustomAssertions.assertThat(inputEntities)
                    .hasSameElementsAs(List.of(responseBody))
                    .withCustomComparator(getUserWithDetailsResponseUserEntityModelComparator());
        }

        @Test
        @WithMockUser
        public void should_return_403_to_unauthorized_user() throws Exception {
            //given
            saveDefaultUsers();

            //when
            var result = mock.perform(
                    get("/user-management/get-all/with-details")
            ).andExpect(
                    //then
                    status().is(403)
            );
        }

        @Test
        public void should_return_403_to_non_logged_user() throws Exception {
            //given
            saveDefaultUsers();

            //when
            var result = mock.perform(
                    get("/user-management/get-all/with-details")
            ).andExpect(
                    //then
                    status().is(403)
            );
        }
    }

    @Test
    @WithMockUser(authorities = UserAPIAuthorities.GET_DETAILS)
    public void should_return_user_details() throws Exception {
        //given
        var inputEntities = saveDefaultUsers();

        var searchedUser = inputEntities.get(1).getUsername();

        mock.perform(
                get("/user-management/user/"+searchedUser)
        ).andExpect(
                status().is(200)
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON)
        );
    }

    
    private ModelComparator<UserEntity, GetUserWithDetailsResponse> getUserWithDetailsResponseUserEntityModelComparator() {
        return (u, r) -> {
            Assertions.assertThat(r.usersRoles()).hasSameElementsAs(u.getRoles().stream().map(RoleEntity::getRole).toList());
            Assertions.assertThat(r.usersPermissions()).hasSameElementsAs(u.getPermissions().stream().map(PermissionEntity::getAuthority).toList());

            return r.isLocked().equals(u.getLocked()) &&
                    r.username().equals(u.getUsername()) &&
                    r.accountType().equals(u.getAccountType()) &&
                    Objects.equals(r.email(), u.getUserDetails().getEmail());
        };
    }

    private List<UserEntity> saveUsers(UserEntity... entities) {
        var listOfEntities = List.of(entities);
        userRepository.saveAll(listOfEntities);

        return listOfEntities;
    }

    /**
     * saves 3 users:\n
     * ROOT
     * Alice
     * bob
     */
    private List<UserEntity> saveDefaultUsers() {
        //given
        return saveUsers(
                aRootUser().build(),
                anAliceUser().build(),
                aBobUser().build()
        );
    }

    private <T> T getBody(MvcResult result, Class<T> clazz) throws Exception {
        var response = result.getResponse();
        var rawBody = response.getContentAsString();

        ObjectMapper mapper = new JsonMapper();
        return mapper.readValue(rawBody, clazz);
    }

}