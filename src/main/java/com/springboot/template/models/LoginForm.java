package com.springboot.template.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginForm {

    @Email(message = "{email.format}")
    @NotEmpty(message = "{email.not.empty}")
    private String email;

    @NotEmpty(message = "{password.not.empty}")
    private String password;

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