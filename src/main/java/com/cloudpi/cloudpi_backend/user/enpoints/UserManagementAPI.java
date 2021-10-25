package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.cloudpi.cloudpi_backend.user.enpoints.UserAPIAuthorities.GET_DETAILS;

@RequestMapping("/user-management/")
public interface UserManagementAPI {

//    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "get-all", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    List<GetUserResponse> getAllUsers();

    @Secured(UserAPIAuthorities.GET_DETAILS)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "get-all/with-details")
    List<GetUserWithDetailsResponse> getAllUsersWithDetails();

//    @Secured(UserAPIAuthorities.GET_DETAILS)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user/{username}")
    GetUserWithDetailsResponse getUserDetails(@PathVariable(name = "username") String username);

//    @Secured(UserAPIAuthorities.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("user")
    void createNewUser(@RequestBody @Valid PostUserRequest user);

//    @PreAuthorize("hasAuthority(USER_MODIFY) or" +
//            "#login == principal")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("user/{username}")
    void updateUserDetails(@RequestParam("username") String username,
                           @RequestBody UpdateUserDetailsRequest request);

//    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user/schedule-delete/{username}")
    void scheduleUserDelete(@PathVariable(name = "username") String username);

//    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user/{username}")
    void deleteUser(@PathVariable(name = "username") String username);
}
