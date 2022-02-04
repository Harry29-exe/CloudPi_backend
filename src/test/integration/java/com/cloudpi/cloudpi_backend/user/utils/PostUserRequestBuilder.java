package com.cloudpi.cloudpi_backend.user.utils;

import com.cloudpi.cloudpi_backend.user.api.dto.PostUserRequest;

public final class PostUserRequestBuilder {
    private String nickname;
    private String username;
    private String password;
    private AccountType accountType = AccountType.USER;
    private String email;

    private PostUserRequestBuilder() {
    }

    public static PostUserRequestBuilder aRootUser() {
        var builder = new PostUserRequestBuilder();
        builder.nickname = "mightyRoot";
        builder.username = "ROOT";
        builder.password = "123123";
        builder.accountType = AccountType.ROOT;

        return builder;
    }

    public static PostUserRequestBuilder aBobUser() {
        var builder = new PostUserRequestBuilder();
        builder.nickname = "bobTheWise";
        builder.username = "bob";
        builder.password = "321321";
        builder.accountType = AccountType.USER;

        return builder;
    }

    public static PostUserRequestBuilder anAliceUser() {
        var builder = new PostUserRequestBuilder();
        builder.nickname = "super@lice";
        builder.username = "Alice";
        builder.password = "alice1";
        builder.accountType = AccountType.USER;

        return builder;
    }

    public static PostUserRequestBuilder aPostUserRequest() {
        return new PostUserRequestBuilder();
    }

    public PostUserRequestBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public PostUserRequestBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public PostUserRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public PostUserRequestBuilder withAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public PostUserRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public PostUserRequest build() {
        PostUserRequest postUserRequest = new PostUserRequest();
        postUserRequest.setNickname(nickname);
        postUserRequest.setUsername(username);
        postUserRequest.setPassword(password);
        postUserRequest.setAccountType(accountType);
        postUserRequest.setEmail(email);
        return postUserRequest;
    }
}
