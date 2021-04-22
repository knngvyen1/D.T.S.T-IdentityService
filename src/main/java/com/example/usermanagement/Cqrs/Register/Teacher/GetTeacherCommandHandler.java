package com.example.usermanagement.Cqrs.Register.Teacher;

import com.example.usermanagement.Domain.Model.RoleType;
import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Exception.BadRequestException;
import com.example.usermanagement.Exception.NotFoundException;
import com.example.usermanagement.Repository.IUserRepository;
import com.example.usermanagement.Repository.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetTeacherCommandHandler {
    private IUserRepository userRepository;
    private IUserRoleRepository roleRepository;

    @Autowired
    public GetTeacherCommandHandler(IUserRepository userRepository, IUserRoleRepository roleRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public  User handle(long teacherID)
    {
        Optional<User> founduser = userRepository.findById(teacherID);
        if (founduser.isEmpty())
        throw new NotFoundException("does not exist");

        return founduser.get();
    }
}
