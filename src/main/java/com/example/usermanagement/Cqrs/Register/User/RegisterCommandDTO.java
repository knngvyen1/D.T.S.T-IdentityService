package com.example.usermanagement.Cqrs.Register.User;

import com.example.usermanagement.Domain.Model.Role;
import com.example.usermanagement.Domain.Model.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@FieldMatch(first= "password", second = "repeatedPassword", message = "passwords dont match")
public class RegisterCommandDTO {
    private String email;
    private String name;

//    @Size(min = 5, max = 20, message = "password must be between 5 and 20 characters")
    private String password;
    private String repeatedPassword;
    @JsonIgnore
    private RoleType roleType;


}
