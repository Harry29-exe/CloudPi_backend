package com.cloudpi.cloudpi_backend.user.responses;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.user.dto.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

//TODO zamienić permissions + roles -> AuthorityDTO
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserWithDetailsResponse {
    private @NotNull String username;
    private @NotNull String nickname;
    private @Nullable String email;
    private @NotNull Boolean isLocked;
    private @NotNull AccountType accountType;
    private @NotNull List<AuthorityDTO> authorities;


    public static GetUserWithDetailsResponse from(UserWithDetailsDTO user) {
        var details = user.getUserDetails();
        var authorities = new ArrayList<>(user.getPermissions());
        authorities.addAll(user.getRoles());
        return new GetUserWithDetailsResponse(
                user.getUsername(),
                details.getNickname(),
                details.getEmail(),
                user.getLocked(),
                details.getAccountType(),
                authorities
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetUserWithDetailsResponse that = (GetUserWithDetailsResponse) o;
        return Objects.equal(username, that.username) &&
                Objects.equal(nickname, that.nickname) &&
                Objects.equal(email, that.email) &&
                Objects.equal(isLocked, that.isLocked) &&
                accountType == that.accountType &&
                authorities.containsAll(that.authorities) &&
                that.authorities.containsAll(authorities);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
