package com.example.usermanagement.Cqrs.Register.Teacher;

import com.example.usermanagement.Domain.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterTeacherCommandResult {
    private User createdTeacher;
}
