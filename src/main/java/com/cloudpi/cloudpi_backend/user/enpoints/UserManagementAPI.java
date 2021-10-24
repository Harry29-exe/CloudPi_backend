package com.cloudpi.cloudpi_backend.user.enpoints;

import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.cloudpi.cloudpi_backend.user.requests.UpdateUserDetailsRequest;
import com.cloudpi.cloudpi_backend.user.responses.GetUserResponse;
import com.cloudpi.cloudpi_backend.user.responses.GetUserWithDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


public interface UserManagementAPI {

//    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "get-all", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    List<GetUserResponse> getAllUsers();

//    @Secured(UserAPIAuthorities.GET_DETAILS)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "get-all/with-details")
    List<GetUserWithDetailsResponse> getAllUsersWithDetails();

//    @Secured(UserAPIAuthorities.GET_DETAILS)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("user/{nickname}")
    GetUserWithDetailsResponse getUserDetails(@PathVariable(name = "nickname") String nickname);

//    @Secured(UserAPIAuthorities.CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("user")
    void createNewUser(@RequestBody @Valid PostUserRequest user);

//    @PreAuthorize("hasAuthority(USER_MODIFY) or" +
//            "#login == principal")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("user/{nickname}")
    void updateUserDetails(@RequestParam("nickname") String nickname,
                           @RequestBody UpdateUserDetailsRequest request);

//    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user/schedule-delete/{nickname}")
    void scheduleUserDelete(@PathVariable(name = "nickname") String nickname);

//    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user/{nickname}")
    void deleteUser(@PathVariable(name = "nickname") String nickname);
}
