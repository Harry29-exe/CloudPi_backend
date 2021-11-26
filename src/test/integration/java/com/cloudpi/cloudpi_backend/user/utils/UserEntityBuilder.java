package com.cloudpi.cloudpi_backend.user.utils;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.files.filesystem.entities.VirtualDriveEntity;
import com.cloudpi.cloudpi_backend.user.dto.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserDeleteEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;

import java.util.Set;

public final class UserEntityBuilder {
    private Long id;
    private String login;
    private String username;
    private String password;
    private Boolean locked = false;
    private AccountType accountType = AccountType.USER;
    private UserDetailsEntity userDetails;
    private UserDeleteEntity userDeleteSchedule;
    private Set<RoleEntity> roles;
    private Set<PermissionEntity> permissions;
    private VirtualDriveEntity userDrive;

    private UserEntityBuilder() {
    }

    public static UserEntityBuilder aRootUser() {
        var builder = new UserEntityBuilder();
        builder.login = "mightyRoot";
        builder.username = "ROOT";
        builder.password = "123";
        builder.accountType = AccountType.ROOT;
        builder.userDetails = new UserDetailsEntity(null, null);

        return builder;
    }

    public static UserEntityBuilder aBobUser() {
        var builder = new UserEntityBuilder();
        builder.login = "bobTheWise";
        builder.username = "bob";
        builder.password = "321";
        builder.accountType = AccountType.USER;
        builder.userDetails = new UserDetailsEntity();

        return builder;
    }

    public static UserEntityBuilder anAliceUser() {
        var builder = new UserEntityBuilder();
        builder.login = "super@lice";
        builder.username = "Alice";
        builder.password = "alice";
        builder.accountType = AccountType.USER;
        builder.userDetails = new UserDetailsEntity();

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

    public UserEntityBuilder withLogin(String login) {
        this.login = login;
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

    public UserEntityBuilder withAccountType(AccountType accountType) {
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

    public UserEntityBuilder withFilesInfo(VirtualDriveEntity userDrive) {
        this.userDrive = userDrive;
        return this;
    }

    public UserEntity build() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setLogin(login);
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setLocked(locked);
        userEntity.setAccountType(accountType);
        userEntity.setUserDetails(userDetails);
        userEntity.setUserDeleteSchedule(userDeleteSchedule);
        userEntity.setRoles(roles);
        userEntity.setPermissions(permissions);
        userEntity.setUserDrive(userDrive);

        userEntity.getUserDetails().setUser(userEntity);
        return userEntity;
    }
}
