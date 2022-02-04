package com.cloudpi.cloudpi_backend.user.utils;

import com.cloudpi.cloudpi_backend.user.services.dto.CreateUser;

public final class CreateUserValBuilder {
    String username;
    String nonEncodedPassword;
    String nickname;
    AccountType accountType;
    String email;

    private CreateUserValBuilder() {
    }

    public static CreateUserValBuilder aCreateUserVal() {
        return new CreateUserValBuilder();
    }

    public static CreateUserValBuilder aRootUser() {
        var builder = new CreateUserValBuilder();
        builder.nickname = "mightyRoot";
        builder.username = "ROOT";
        builder.nonEncodedPassword = "123123";
        builder.accountType = AccountType.ROOT;

        return builder;
    }

    public static CreateUserValBuilder aBobUser() {
        var builder = new CreateUserValBuilder();
        builder.nickname = "bobTheWise";
        builder.username = "bob";
        builder.nonEncodedPassword = "321321";
        builder.accountType = AccountType.USER;

        return builder;
    }

    public static CreateUserValBuilder anAliceUser() {
        var builder = new CreateUserValBuilder();
        builder.nickname = "super Alice";
        builder.username = "Alice";
        builder.nonEncodedPassword = "alice1";
        builder.accountType = AccountType.USER;

        return builder;
    }

    public CreateUserValBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public CreateUserValBuilder withNonEncodedPassword(String nonEncodedPassword) {
        this.nonEncodedPassword = nonEncodedPassword;
        return this;
    }

    public CreateUserValBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public CreateUserValBuilder withAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public CreateUserValBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateUser build() {
        return new CreateUser(username, nonEncodedPassword, nickname, accountType, email);
    }
}
