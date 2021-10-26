package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.test.utils.WithUser;
import com.cloudpi.cloudpi_backend.test.utils.assertions.CustomAssertions;
import com.cloudpi.cloudpi_backend.test.utils.assertions.ModelComparator;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
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
abstract class UserManagementControllerTestTemplate {

    @Autowired
    protected MockMvc mock;
    @Autowired
    protected UserService userService;
    
    protected ModelComparator<UserWithDetailsDTO, GetUserWithDetailsResponse> getGetUserWithDetailsComparator() {
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
    protected List<UserWithDetailsDTO> beforeTestSaveDefaultUsers() {
        if(SecurityContextHolder.getContext().getAuthentication() != null){
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

        for(var user : usersToSave) {
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
//        @WithMockUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @WithUser(username = "we", authorities = UserAPIAuthorities.MODIFY)
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
    @WithMockUser(setupBefore = TestExecutionEvent.TEST_EXECUTION)
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
        @WithMockUser(authorities = {UserAPIAuthorities.GET_DETAILS})
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
        @WithMockUser
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
    @WithMockUser
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
    @WithMockUser(authorities = UserAPIAuthorities.GET_DETAILS)
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
    @WithMockUser(username = "bob")
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
        var searchedUsername = usersInDB.get(0);

        //when
        mock.perform(
                get(testingEndpoint+searchedUsername)
        ).andExpect(
                //then
                status().is(403)
        ).andReturn();
    }

    @Test
    public void should_return_403_to_user_without_permissions() throws Exception {
        //given
        var searchedUsername = usersInDB.get(0).getUsername();

        //when
        mock.perform(
                get(testingEndpoint+searchedUsername)
        ).andExpect(
                //then
                status().is(403)
        ).andReturn();
    }

    @Test
    @WithMockUser(authorities = UserAPIAuthorities.GET_DETAILS)
    public void should_return_404_when_sought_user_does_not_exist() throws Exception {
        //given
        var searchedUsername = usersInDB.get(0).getUsername();

        //when
        var result = mock.perform(
                get(testingEndpoint + searchedUsername)
        ).andExpect(
                status().is(404)
        ).andExpect(
                content().contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
    }
}