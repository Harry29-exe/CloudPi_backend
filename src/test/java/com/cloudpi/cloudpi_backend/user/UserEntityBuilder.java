package com.cloudpi.cloudpi_backend.user;

import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.files.permissions.entities.FilePermissionEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DriveObjectEntity;
import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.cloudpi.cloudpi_backend.user.UserTestUtils.passwordEncoder;

public class UserEntityBuilder {
    private Long id = 2L;
    private String username = "steve";
    private String email = "steve@we.com";
    private String nickname = "n steve";
    private String password = passwordEncoder.encode("123");
    private Boolean locked = false;
    private String accountType = AccountType.USER;
    private Set<RoleEntity> roles = new HashSet<>();
    private Set<PermissionEntity> permissions = new HashSet<>();
    private List<DriveObjectEntity> filesInfo = new ArrayList<>();
    private List<FilePermissionEntity> filesPermissions = new ArrayList<>();

    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }

    public static UserEntityBuilder fastBuilder(Long id, String username) {
        var temp = new UserEntityBuilder();
        temp.id = id;
        temp.username = username;
        temp.nickname = "n " + username;
        temp.email = username + "@we.com";
        return temp;
    }

    public UserEntityBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntityBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEntityBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntityBuilder setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserEntityBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntityBuilder setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public UserEntityBuilder setAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public UserEntityBuilder setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
        return this;
    }

    public UserEntityBuilder setFilesInfo(List<DriveObjectEntity> filesInfo) {
        this.filesInfo = filesInfo;
        return this;
    }

    public UserEntityBuilder setFilesPermissions(List<FilePermissionEntity> filesPermissions) {
        this.filesPermissions = filesPermissions;
        return this;
    }

    public UserEntity build() {
        return new UserEntity(id, username, email, nickname, password, locked, accountType, roles, permissions, filesInfo, filesPermissions);
    }
}