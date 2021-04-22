package com.example.usermanagement.Cqrs.Register.RegisterBackend;

import com.example.usermanagement.Domain.Model.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveUserDTO {
    private long userID;
    private String name;
    private String email;
    private RoleType roleType;
}
