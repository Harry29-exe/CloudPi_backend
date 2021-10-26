package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.test.utils.CustomAssertions;
import com.cloudpi.cloudpi_backend.test.utils.ModelComparator;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

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
    private UserService userService;

    @Nested
    @DisplayName("/user-management/get-all")
    @Transactional
    class GetALL {
        private List<UserWithDetailsDTO> inputEntities;

        @WithMockUser(authorities = UserAPIAuthorities.CREATE)
        @BeforeEach
        public void setUp() {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken("user", "123",
                            List.of(new SimpleGrantedAuthority(UserAPIAuthorities.CREATE)))
            );
            inputEntities = saveDefaultUsers();
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        @Test
        @WithMockUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
        public void should_return_all_users() throws Exception {
            //given

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

//        @Test
//        @WithMockUser
//        public void should_return_empty_list() throws Exception {
//            //given
//
//            //when
//            var result = mock.perform(
//                    get("/user-management/get-all")
//            ).andExpect(
//                    status().is2xxSuccessful()
//            ).andExpect(
//                    content().contentType(MediaType.APPLICATION_JSON)
//            ).andReturn();
//
//
//            var response = result.getResponse();
//            var rawBody = response.getContentAsString();
//
//            ObjectMapper mapper = new JsonMapper();
//            var responseBody = mapper.readValue(rawBody, GetUserResponse[].class);
//
//            //then
//            assert responseBody.length == 0;
//        }

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
                    .withCustomComparator(getGetUserWithDetailsComparator());
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

    @Nested
    @DisplayName("user-management/{username}")
    @Transactional
    class GetUserDetails {
        @Test
        @WithMockUser(authorities = UserAPIAuthorities.GET_DETAILS)
        public void should_return_user_details() throws Exception {
            //given
            var inputEntities = saveDefaultUsers();
            var searchedUser = inputEntities.get(0);
            var searchedUsername = searchedUser.getUsername();

            //when
            var result = mock.perform(
                    get("/user-management/" + searchedUsername)
            ).andExpect(
                    status().is(200)
            ).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON)
            ).andReturn();

            //then
            var responseBody = getBody(result, GetUserWithDetailsResponse.class);
            assert getGetUserWithDetailsComparator().areEquals(searchedUser, responseBody);
        }

        @Test
        @WithMockUser(username = "bob")
        public void should_return_users_own_details() throws Exception {
            //given
            var inputEntities = saveDefaultUsers();
            var searchedUser = inputEntities.stream()
                    .filter(u -> u.getUsername().equals("bob"))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);
            var searchedUsername = "bob";

            //when
            var result = mock.perform(
                    get("/user-management/"+searchedUsername)
            ).andExpect(
                    status().is(200)
            ).andReturn();

            //then
            var responseBody = getBody(result, GetUserWithDetailsResponse.class);
            assert getGetUserWithDetailsComparator().areEquals(searchedUser, responseBody);
        }

        @Test
        public void should_return_403_to_non_logged_user() throws Exception {
            //given
            var inputEntities = saveDefaultUsers();
            var searchedUsername = inputEntities.get(0);

            //when
            mock.perform(
                    get("/user-management/"+searchedUsername)
            ).andExpect(
                    //then
                    status().is(403)
            ).andReturn();
        }

        @Test
        public void should_return_403_to_user_without_permissions() throws Exception {
            //given
            var inputEntities = saveDefaultUsers();
            var searchedUsername = inputEntities.get(0).getUsername();

            //when
            mock.perform(
                    get("/user-management/"+searchedUsername)
            ).andExpect(
                    //then
                    status().is(403)
            ).andReturn();
        }

        @Test
        @WithMockUser(authorities = UserAPIAuthorities.GET_DETAILS)
        public void should_return_404_when_sought_user_does_not_exist() throws Exception {
            //given
            var inputEntities = saveDefaultUsers();
            var searchedUsername = inputEntities.get(0).getUsername();

            //when
            var result = mock.perform(
                    get("/user-management/" + searchedUsername)
            ).andExpect(
                    status().is(404)
            ).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON)
            ).andReturn();
        }
    }

    
    private ModelComparator<UserWithDetailsDTO, GetUserWithDetailsResponse> getGetUserWithDetailsComparator() {
        return (u, r) -> {
            Assertions.assertThat(r.usersRoles()).hasSameElementsAs(u.getRoles().stream().map(AuthorityDTO::authority).toList());
            Assertions.assertThat(r.usersPermissions()).hasSameElementsAs(u.getPermissions().stream().map(AuthorityDTO::authority).toList());

            return r.isLocked().equals(u.getLocked()) &&
                    r.username().equals(u.getUsername()) &&
                    r.accountType().equals(u.getAccountType()) &&
                    Objects.equals(r.email(), u.getUserDetails().getEmail());
        };
    }

    /**
     * <h3>Saves 3 users:</h3>
     * ROOT <br/>
     * Alice <br/>
     * bob <br/>
     */
    private List<UserWithDetailsDTO> saveDefaultUsers() {
        //given
        var usersToSave = List.of(
                aRootUser().build().toUserWithDetailsDTO(),
                anAliceUser().build().toUserWithDetailsDTO(),
                aBobUser().build().toUserWithDetailsDTO()
        );

        for(var user : usersToSave) {
            userService.createUserWithDefaultAuthorities(user, "123");
        }

        return usersToSave;
    }

    private <T> T getBody(MvcResult result, Class<T> clazz) throws Exception {
        var response = result.getResponse();
        var rawBody = response.getContentAsString();

        ObjectMapper mapper = new JsonMapper();
        return mapper.readValue(rawBody, clazz);
    }

}