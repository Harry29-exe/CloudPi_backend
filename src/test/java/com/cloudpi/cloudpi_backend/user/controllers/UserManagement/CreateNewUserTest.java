package com.cloudpi.cloudpi_backend.user.controllers.UserManagement;

import com.cloudpi.cloudpi_backend.exepctions.authorization.IllegalIPException;
import com.cloudpi.cloudpi_backend.exepctions.authorization.NoRequiredPermissionException;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.UsernameIsTakenException;
import com.cloudpi.cloudpi_backend.security.dto.CloudPiAuthentication;
import com.cloudpi.cloudpi_backend.user.controllers.AccountType;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.persistence.EntityExistsException;
import java.util.Optional;

import static com.cloudpi.cloudpi_backend.user.UserTestUtils.createDefaultUserDTO;
import static com.cloudpi.cloudpi_backend.user.UserTestUtils.createRootUserDTO;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.context.TestSecurityContextHolder.setAuthentication;

public class CreateNewUserTest extends UserManagementControllerTest {

    @Test
    void should_throw_Exception_when_user_does_not_have__USER_CREATE__permission() {
        //given
        setModificationsOnlyFromLocalNetwork(false);
        var userDto = createDefaultUserDTO();
        var http = new MockHttpServletRequest();
        http.setRemoteAddr("192.168.0.101");
        var auth = new CloudPiAuthentication(
                userDto.getUsername(),
                userDto.getPassword(),
                AccountType.USER.getAuthorities()
        );
        setAuthentication(auth);
        var requestBody = new PostUserRequest("newUsername", "nickname", "password");

        doReturn(Optional.of(userDto)).when(userService).getUser("username");

        //then
        Assertions.assertThrows(NoRequiredPermissionException.class, () -> {
            //when
            userController.createNewUser(requestBody);
        });
    }

    @Test
    void should_throw_Exception_when_request_not_from_local_network() {
        //given
        setModificationsOnlyFromLocalNetwork(true);
        var userDto = createRootUserDTO();
        var http = new MockHttpServletRequest();
        http.setRemoteAddr("109.4.4.105");
        var auth = new CloudPiAuthentication(userDto, AccountType.USER.getAuthorities());
        var requestBody = new PostUserRequest("newUsername", "nickname", "password");

        doReturn(Optional.of(userDto)).when(userService).getUser("username");
        //then
        Assertions.assertThrows(IllegalIPException.class, () -> {
            //when
            userController.createNewUser(requestBody);
        });
    }

    @Test
    void should_throw_Exception_when_username_exists() {
        //given
        setModificationsOnlyFromLocalNetwork(true);
        var userDto = createRootUserDTO();
        var http = new MockHttpServletRequest();
        http.setRemoteAddr("192.168.0.101");
        var auth = new CloudPiAuthentication(userDto, AccountType.USER.getAuthorities());
        var requestBody = new PostUserRequest(userDto.getUsername(), "nickname", "password");

        doThrow(EntityExistsException.class).when(userService).getUser("username");
        //then
        Assertions.assertThrows(UsernameIsTakenException.class, () -> {
            //when
            userController.createNewUser(requestBody);
        });
    }
}
