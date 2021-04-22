package com.example.usermanagement.Cqrs.Register.Teacher;

import com.example.usermanagement.Domain.Model.RoleType;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCommandDTO {
    private String email;
    private String name;
    //    @JsonIgnore
    private RoleType roleType;
}
