package com.timecapsule.api.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Email
    private String email;
}
