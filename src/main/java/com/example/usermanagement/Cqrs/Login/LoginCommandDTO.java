package com.example.usermanagement.Cqrs.Login;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginCommandDTO {
    private String email;
    private String password;
}
