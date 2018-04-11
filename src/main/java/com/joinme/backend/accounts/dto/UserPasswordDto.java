package com.joinme.backend.accounts.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Created by Nicole, August 2017.
 */
public class UserPasswordDto {
    @NotNull
    @Size(min = 1, max = 40)
    private String username;

    @NotNull
    @Size(min = 1, max = 50)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
