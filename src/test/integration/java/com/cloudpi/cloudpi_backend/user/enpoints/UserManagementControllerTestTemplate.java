package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.utils.mock_mvc_users.WithUser;
import com.cloudpi.cloudpi_backend.utils.assertions.CustomAssertions;
import com.cloudpi.cloudpi_backend.utils.assertions.ModelComparator;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.cloudpi.cloudpi_backend.utils.assertions.CustomAssertions;
import com.cloudpi.cloudpi_backend.utils.assertions.ModelComparator;
import com.cloudpi.cloudpi_backend.utils.mock_mvc_users.WithUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.With;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.cloudpi.cloudpi_backend.user.utils.UserEntityBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
abstract class UserManagementControllerTestTemplate {

    @Autowired
    protected MockMvc mock;
    @Autowired
    protected UserService userService;

    protected ModelComparator<UserWithDetailsDTO, GetUserWithDetailsResponse> getGetUserWithDetailsComparator() {
        return (u, r) -> {
            if (u.getRoles() != null) {
                if (r.usersRoles() != null && r.usersRoles().size() > 0) {
                    throw new AssertionError("User DTO object has not any role while");
                }
                Assertions.assertThat(r.usersRoles()).hasSameElementsAs(u.getRoles().stream().map(AuthorityDTO::authority).toList());
            }
            if (u.getPermissions() != null) {
                if (r.usersPermissions() != null && r.usersPermissions().size() > 0) {
                    throw new AssertionError();
                }
                Assertions.assertThat(r.usersPermissions()).hasSameElementsAs(u.getPermissions().stream().map(AuthorityDTO::authority).toList());
            }
            return Objects.equals(r.isLocked(), u.getLocked()) &&
                    Objects.equals(r.username(), u.getUsername()) &&
                    Objects.equals(r.accountType(), u.getAccountType()) &&
                    Objects.equals(r.email(), u.getUserDetails().getEmail());
        };
    }

    /**
     * <h3>Saves 3 users:</h3>
     * ROOT <br/>
     * Alice <br/>
     * bob <br/>
     */
    protected List<UserWithDetailsDTO> beforeTestSaveDefaultUsers() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            throw new IllegalStateException("""
                    User should be injected after @BeforeEach is invoked, please add:
                    setupBefore = TestExecutionEvent.TEST_EXECUTION
                    to your @WithMockUser annotation""");
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("user", "123",
                        List.of(new SimpleGrantedAuthority(UserAPIAuthorities.CREATE)))
        );

        var usersToSave = List.of(
                aRootUser().build().toUserWithDetailsDTO(),
                anAliceUser().build().toUserWithDetailsDTO(),
                aBobUser().build().toUserWithDetailsDTO()
        );

        for (var user : usersToSave) {
            userService.createUserWithDefaultAuthorities(user, "123");
        }

        SecurityContextHolder.getContext().setAuthentication(null);

        return usersToSave;
    }

    protected <T> T getBody(MvcResult result, Class<T> clazz) throws Exception {
        var response = result.getResponse();
        var rawBody = response.getContentAsString();

        ObjectMapper mapper = new JsonMapper();
        return mapper.readValue(rawBody, clazz);
    }

    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

@DisplayName("/user-management/get-all")
@Transactional
class GetALL extends UserManagementControllerTestTemplate {

    @Nested
    @DisplayName("With users in db")
    @Transactional
    class WithUsersInDB {
        private List<UserWithDetailsDTO> inputEntities;

        @BeforeEach
        public void setUp() {
            inputEntities = beforeTestSaveDefaultUsers();
        }

        @Test
        @WithUser
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

        @Test
        public void should_return_403_unauthorized() throws Exception {
            //when
            mock.perform(
                    get("/user-management/get-all")
            ).andExpect(
                    status().is(403)
            );
        }
    }

    @Test
    @WithUser
    void should_return_empty_list() throws Exception {
        //when
        var result = mock.perform(
                get("/user-management/get-all")
        ).andExpect(
                status().is(200)
        ).andReturn();

        assert getBody(result, GetUserResponse[].class).length == 0;
    }
}

@DisplayName("/user-management/get-all/with-details")
@Transactional
class GetAllWithDetails extends UserManagementControllerTestTemplate {

    protected static String testingEndpoint = "/user-management/get-all/with-details";

    @Nested
    @Transactional
    @DisplayName("With defaults users in db")
    class WithUsersInDB {
        private List<UserWithDetailsDTO> usersInDB;

        @BeforeEach
        void setUp() {
            usersInDB = beforeTestSaveDefaultUsers();
        }

        @Test
        @WithUser(authorities = {UserAPIAuthorities.GET_DETAILS})
        public void should_return_200_with_userWithDetails_list() throws Exception {

            //when
            var result = mock.perform(
                    get(testingEndpoint)
            ).andExpect(
                    status().is2xxSuccessful()
            ).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON)
            ).andReturn();

            var responseBody = getBody(result, GetUserWithDetailsResponse[].class);
            CustomAssertions.assertThat(usersInDB)
                    .hasSameElementsAs(List.of(responseBody))
                    .withCustomComparator(getGetUserWithDetailsComparator());
        }

        @Test
        @WithUser
        public void should_return_403_to_unauthorized_user() throws Exception {

            //when
            var result = mock.perform(
                    get(testingEndpoint)
            ).andExpect(
                    //then
                    status().is(403)
            );
        }

        @Test
        public void should_return_403_to_non_logged_user() throws Exception {

            //when
            var result = mock.perform(
                    get(testingEndpoint)
            ).andExpect(
                    //then
                    status().is(403)
            );
        }
    }

    @Test
    @WithUser(authorities = UserAPIAuthorities.GET_DETAILS)
    void should_return_empty_list() throws Exception {
        //when
        var result = mock.perform(
                get(testingEndpoint)
        ).andExpect(
                status().is(200)
        ).andReturn();

        assert getBody(result, GetUserResponse[].class).length == 0;
    }
}

@DisplayName("user-management/{username}")
@Transactional
class GetUserDetails extends UserManagementControllerTestTemplate {
    private final String testingEndpoint = "/user-management/";
    private List<UserWithDetailsDTO> usersInDB;

    @BeforeEach
    void setUp() {
        usersInDB = beforeTestSaveDefaultUsers();
    }

    @Test
    @WithUser(authorities = UserAPIAuthorities.GET_DETAILS)
    public void should_return_user_details() throws Exception {
        //given
        var searchedUser = usersInDB.get(0);
        var searchedUsername = searchedUser.getUsername();

        //when
        var result = mock.perform(
                get(testingEndpoint + searchedUsername)
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
    @WithUser(username = "bob")
    public void should_return_users_own_details() throws Exception {
        //given
        var searchedUser = usersInDB.stream()
                .filter(u -> u.getUsername().equals("bob"))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        var searchedUsername = "bob";

        //when
        var result = mock.perform(
                get(testingEndpoint + searchedUsername)
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
        var searchedUsername = usersInDB.get(0).getUsername();

        //when
        mock.perform(
                get(testingEndpoint + searchedUsername)
        ).andExpect(
                //then
                status().is(403)
        ).andReturn();
    }

    @Test
    @WithUser(username = "bob")
    public void should_return_403_to_user_without_permissions() throws Exception {
        //given
        var searchedUsername = usersInDB.get(0).getUsername();

        //when
        mock.perform(
                get(testingEndpoint + searchedUsername)
        ).andExpect(
                //then
                status().is(403)
        ).andReturn();
    }

    @Test
    @WithUser(authorities = UserAPIAuthorities.GET_DETAILS)
    public void should_return_404_when_sought_user_does_not_exist() throws Exception {
        //given
        var searchedUsername = "steve";

        //when
        var result = mock.perform(
                get(testingEndpoint + searchedUsername)
        ).andExpect(
                status().is(404)
        ).andReturn();
    }
}

@DisplayName("/user-management/")
@Transactional
class CreateNewUser extends UserManagementControllerTestTemplate {
    private final String testingEndpoint = "/user-management/";

    private PostUserRequest defaultUser() {
        PostUserRequest user = new PostUserRequest();
        user.setUsername("someone");
        user.setLogin("somebody");
        user.setPassword("secret");
        return user;
    }

    @Test
    @WithUser(authorities = UserAPIAuthorities.CREATE)
    public void should_return_201_when_created() throws Exception {
        //given
        var user = defaultUser();
        //when
        performPost(user).andExpect(
                status().is(201)
        ).andReturn();
    }

    @Test
    @WithUser(username = "bob")
    public void should_return_403_when_no_permission() throws Exception {
        //given
        var user = defaultUser();
        //when
        performPost(user).andExpect(
                status().is(403)
        ).andReturn();
    }

    @Test
    public void should_return_403_when_no_authority() throws Exception {
        //given
        var user = defaultUser();
        //when
        performPost(user).andExpect(
                status().is(403)
        ).andReturn();
    }

    @Test
    @WithUser(authorities = UserAPIAuthorities.CREATE)
    public void should_post_valid_user() throws Exception {
        //given
        var user = defaultUser();
        //when
        performPost(user).andExpect(
                status().is(201)
        ).andReturn();

        var result = mock.perform(
                get(testingEndpoint + "get-all")
        ).andExpect(
                status().is(200)
        ).andReturn();

        //then
        var responseBody = getBody(result, GetUserResponse[].class);
        assert user.getUsername().equals(responseBody[0].getUsername());
    }

    private ResultActions performPost(PostUserRequest user) throws Exception {
        return mock.perform(
                post(testingEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user))
        );
    }
}

//todo update nie zmienia username, jedynie inne atrybuty
@DisplayName("/user-management/{username} PATCH")
@Transactional
class UpdateUser extends UserManagementControllerTestTemplate {
    private final String testingEndpoint = "/user-management/";
    private List<UserWithDetailsDTO> usersInDB;

    @BeforeEach
    void setUp() {
        usersInDB = beforeTestSaveDefaultUsers();
    }

    @Test
    public void should_return_403_when_no_authority() throws Exception {
        //given
        String username = "bob";
        var detailsRequest = defaultUserDetails();

        //when
        mock.perform(
                patch(testingEndpoint + username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(detailsRequest))
        ).andExpect(
                status().is(403)
        ).andReturn();
    }

    @Test
    @WithUser(username = "Alice")
    public void should_return_403_when_no_permission() throws Exception {
        //given
        String username = "bob";
        var detailsRequest = defaultUserDetails();

        //when
        mock.perform(
                patch(testingEndpoint + username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(detailsRequest))
        ).andExpect(
                status().is(403)
        ).andReturn();
    }

    @Test
    @WithUser(authorities ={UserAPIAuthorities.MODIFY, UserAPIAuthorities.GET_DETAILS})
    public void should_update_user_with_authority() throws Exception {
        //given
        String username = "bob";
        String newUsername = "someone";
        var detailsRequest = defaultUserDetails();

        //when
        mock.perform(
                patch(testingEndpoint + username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(detailsRequest))
        ).andExpect(
                status().is(201)
        ).andReturn();

        var result = mock.perform(
                get(testingEndpoint + newUsername)
        ).andExpect(
                status().is2xxSuccessful()
        ).andReturn();

        //then
        var responseBody = getBody(result, GetUserWithDetailsResponse.class);
        assert "someone".equals(responseBody.username());
    }

    //TODO poprawić jwt[princibal: bob] -> change username -> principal != username
    @Test
    @WithUser(username = "bob")
    public void should_update_users_own_details() throws Exception {
        //given
        String username = "bob";
        String newUsername = "someone";
        var detailsRequest = defaultUserDetails();

        //when
        mock.perform(
                patch(testingEndpoint + username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(detailsRequest))
        ).andExpect(
                status().is(201)
        ).andReturn();

        var result = mock.perform(
                get(testingEndpoint + newUsername)
        ).andExpect(
                status().is2xxSuccessful()
        ).andReturn();

        //then
        var responseBody = getBody(result, GetUserWithDetailsResponse.class);
        assert "someone".equals(responseBody.username());
    }

    private UpdateUserDetailsRequest defaultUserDetails() {
        return new UpdateUserDetailsRequest("someone", "someone@gmail.com", null);
    }
}

//todo przygotowac jak bedzie endpoint do tego
@DisplayName("/user-management/{username} DELETE")
@Transactional
class ScheduleUserDelete extends UserManagementControllerTestTemplate {

}
//todo zbadac NestedServletException po probie wyslania zapytania po usunieciu usera (metoda hould_delete_with_authority)
@DisplayName("/user-management/{username}/delete-now")
@Transactional
class DeleteUser extends UserManagementControllerTestTemplate {
    private final String testingEndpoint = "/user-management/";
    private List<UserWithDetailsDTO> usersInDB;

    @BeforeEach
    void setUp() {
        usersInDB = beforeTestSaveDefaultUsers();
    }

    @Test
    public void should_return_403_when_no_authority() throws Exception {
        //given
        String userToDelete = "bob";

        //when
        mock.perform(
                delete(testingEndpoint + userToDelete + "/delete-now")
        ).andExpect(
                status().is(403)
        ).andReturn();
    }

    @Test
    @WithUser(username = "Alice")
    public void should_return_403_when_no_permission() throws Exception {
        //given
        String userToDelete = "bob";

        //when
        mock.perform(
                delete(testingEndpoint + userToDelete + "/delete-now")
        ).andExpect(
                status().is(403)
        ).andReturn();
    }

    @Test
    @WithUser(username = "bob")
    public void should_delete_users_account() throws Exception {
        //given
        String userToDelete = "bob";

        //when
        mock.perform(
                delete(testingEndpoint + userToDelete + "/delete-now")
        ).andExpect(
                status().is(200)
        ).andReturn();
    }

    //TODO virtual drive nie gubi dzieci w db
    @Test
    @WithUser(authorities = {UserAPIAuthorities.DELETE, UserAPIAuthorities.GET_DETAILS})
    public void should_delete_with_authority() throws Exception {
        //given
        String userToDelete = "bob";

        //when
        mock.perform(
                delete(testingEndpoint + userToDelete + "/delete-now")
        ).andExpect(
                status().is(200)
        );

        //then
        var result = mock.perform(
                get(testingEndpoint + "get-all")
        ).andReturn();

        var responseBody = getBody(result, GetUserResponse[].class);

        //then
        assert responseBody.length == 2;
    }
}