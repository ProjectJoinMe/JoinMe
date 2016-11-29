package com.joinme.backend.accounts.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountRegistrationData {
    @NotNull
    @Size(min = 1, max = 40)
    private String username;

    @NotNull
    @Pattern(regexp = ".+@.+")
    @Size(min = 1, max = 250)
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
