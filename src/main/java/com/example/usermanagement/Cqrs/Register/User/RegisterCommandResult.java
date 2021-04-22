package com.example.usermanagement.Cqrs.Register.User;

import com.example.usermanagement.Domain.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCommandResult {
    private User createdUser;
}
