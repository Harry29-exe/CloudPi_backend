package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import com.cloudpi.cloudpi_backend.user.services.UserService;
import com.cloudpi.cloudpi_backend.utils.mock_mvc_users.WithUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Objects;

import static com.cloudpi.cloudpi_backend.user.utils.CreateUserValBuilder.*;
import static com.cloudpi.cloudpi_backend.utils.mock_auth.AuthenticationSetter.clearAuth;
import static com.cloudpi.cloudpi_backend.utils.mock_auth.AuthenticationSetter.setRootAuth;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
abstract class UserManagementControllerTestTemplate {

    @Autowired
    protected MockMvc mock;
    @Autowired
    protected UserService userService;

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
        setRootAuth();

        var usersToSave = List.of(
                aRootUser().build(),
                anAliceUser().build(),
                aBobUser().build()
        );


        for (var user : usersToSave) {
            userService.createUserWithDefaultAuthorities(user);
        }
        var savedUsers = userService.getAllUsersWithDetails();
        clearAuth();

        return savedUsers;
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
class GetALL extends UserManagementControllerTestTemplate {

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

@DisplayName("With users in db")
class WithUsersInDB extends UserManagementControllerTestTemplate {
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
                    userEntity.getUserDetails().getNickname().equals(userInResponse.getNickname()) &&
                            userEntity.getUserDetails().getAccountType().equals(userInResponse.getAccountType())
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

@DisplayName("/user-management/get-all/with-details")
class GetAllWithDetails extends UserManagementControllerTestTemplate {

    protected static String testingEndpoint = "/user-management/get-all/with-details";

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

@DisplayName("With defaults users in db")
class GetAllWithDetails_WithUsersInDB extends UserManagementControllerTestTemplate {
    private List<UserWithDetailsDTO> usersInDB;

    protected static String testingEndpoint = "/user-management/get-all/with-details";

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

        assertThat(usersInDB.stream().map(GetUserWithDetailsResponse::from))
                .hasSameElementsAs(List.of(responseBody));
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

@DisplayName("user-management/{username}")
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
        assert responseBody.equals(GetUserWithDetailsResponse
                .from(searchedUser));
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
//        assert getGetUserWithDetailsComparator().areEquals(searchedUser, responseBody);
        assert responseBody.equals(GetUserWithDetailsResponse.from(searchedUser));
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
class CreateNewUser extends UserManagementControllerTestTemplate {
    private final String testingEndpoint = "/user-management/";

    private PostUserRequest defaultUser() {
        PostUserRequest user = new PostUserRequest();
        user.setUsername("someone");
        user.setNickname("somebody");
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
        assert user.getNickname().equals(responseBody[0].getNickname());
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
    @WithUser(authorities = {UserAPIAuthorities.MODIFY, UserAPIAuthorities.GET_DETAILS})
    public void should_update_user_with_authority() throws Exception {
        //given
        String username = "bob";
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
                get(testingEndpoint + username)
        ).andExpect(
                status().is2xxSuccessful()
        ).andReturn();

        //then
        var responseBody = getBody(result, GetUserWithDetailsResponse.class);
        assert Objects.equals(detailsRequest.getNickname(), responseBody.getNickname());
    }

    //TODO poprawić jwt[princibal: bob] -> change username -> principal != username
    @Test
    @WithUser(username = "bob")
    public void should_update_users_own_details() throws Exception {
        //given
        String username = "bob";
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
                get(testingEndpoint + username)
        ).andExpect(
                status().is2xxSuccessful()
        ).andReturn();

        //then
        var responseBody = getBody(result, GetUserWithDetailsResponse.class);
        assert Objects.equals(detailsRequest.getNickname(), responseBody.getNickname());
    }

    private UpdateUserDetailsRequest defaultUserDetails() {
        return new UpdateUserDetailsRequest("someone", "someone@gmail.com", null);
    }
}

//todo przygotowac jak bedzie endpoint do tego
@DisplayName("/user-management/{username} DELETE")
class ScheduleUserDelete extends UserManagementControllerTestTemplate {

}

//todo zbadac NestedServletException po probie wyslania zapytania po usunieciu usera (metoda hould_delete_with_authority)
@DisplayName("/user-management/{username}/delete-now")
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

        System.out.println();
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
        ).andExpect(
                status().is2xxSuccessful()
        ).andReturn();

        var responseBody = getBody(result, GetUserResponse[].class);

        //then
        assert responseBody.length == 2;
    }
}