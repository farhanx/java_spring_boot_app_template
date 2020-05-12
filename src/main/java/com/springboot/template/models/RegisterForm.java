package com.springboot.template.models;

import com.springboot.template.models.annotations.MatchField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@MatchField.List({
        @MatchField(first = "password", second = "confirmPassword", message = "{password.confirm.password.not.same}"),
        @MatchField(first = "email", second = "confirmEmail", message = "{email.confirm.email.not.same}")
})
@Getter @Setter
public class RegisterForm {

    @Email(message = "{email.format}")
    @NotEmpty(message = "{email.not.empty}")
    private String email;

    @Email(message = "{email.format}")
    @NotEmpty(message = "{confirm.email.not.empty}")
    private String confirmEmail;

    @NotEmpty(message = "{password.not.empty}")
    private String password;

    @NotEmpty(message = "{confirm.password.not.empty}")
    private String confirmPassword;

}