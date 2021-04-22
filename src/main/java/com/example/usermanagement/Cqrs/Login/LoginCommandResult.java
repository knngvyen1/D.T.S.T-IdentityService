package com.example.usermanagement.Cqrs.Login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCommandResult {
    private String accessToken;
    private String refreshToken;
}
