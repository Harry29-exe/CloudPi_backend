package com.cloudpi.cloudpi_backend.user.utils;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.DriveObjectEntity;
import com.cloudpi.cloudpi_backend.files.permissions.entities.FilePermissionEntity;
import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserDeleteEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;

import java.util.List;
import java.util.Set;

public final class UserEntityBuilder {
    private Long id;
    private String username;
    private String password;
    private Boolean locked = false;
    private String accountType = AccountType.USER;
    private UserDetailsEntity userDetails;
    private UserDeleteEntity userDeleteSchedule;
    private Set<RoleEntity> roles;
    private Set<PermissionEntity> permissions;
    private List<DriveObjectEntity> filesInfo;
    private List<FilePermissionEntity> filesPermissions;

    private UserEntityBuilder() {
    }

    public static UserEntityBuilder aRootUser() {
        var builder = new UserEntityBuilder();
        builder.username = "ROOT";
        builder.password = "123";
        builder.accountType = AccountType.ROOT;
        builder.userDetails = new UserDetailsEntity("mighty root", null, null);

        return builder;
    }

    public static UserEntityBuilder anUserEntity() {
        return new UserEntityBuilder();
    }

    public UserEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserEntityBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserEntityBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserEntityBuilder withLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public UserEntityBuilder withAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public UserEntityBuilder withUserDetails(UserDetailsEntity userDetails) {
        this.userDetails = userDetails;
        return this;
    }

    public UserEntityBuilder withUserDeleteSchedule(UserDeleteEntity userDeleteSchedule) {
        this.userDeleteSchedule = userDeleteSchedule;
        return this;
    }

    public UserEntityBuilder withRoles(Set<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public UserEntityBuilder withPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
        return this;
    }

    public UserEntityBuilder withFilesInfo(List<DriveObjectEntity> filesInfo) {
        this.filesInfo = filesInfo;
        return this;
    }

    public UserEntityBuilder withFilesPermissions(List<FilePermissionEntity> filesPermissions) {
        this.filesPermissions = filesPermissions;
        return this;
    }

    public UserEntity build() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setLocked(locked);
        userEntity.setAccountType(accountType);
        userEntity.setUserDetails(userDetails);
        userEntity.setUserDeleteSchedule(userDeleteSchedule);
        userEntity.setRoles(roles);
        userEntity.setPermissions(permissions);
        userEntity.setUsersDrives(filesInfo);
        userEntity.setFilesPermissions(filesPermissions);
        return userEntity;
    }
}
