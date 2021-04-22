package com.example.usermanagement.Security;

import com.example.usermanagement.Domain.Model.Role;
import com.example.usermanagement.Domain.Model.RoleType;
import com.example.usermanagement.Repository.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleLoader implements ApplicationRunner {

    private IUserRoleRepository roleRepository;

    @Autowired
    public RoleLoader(IUserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args){
        roleRepository.save(new Role(RoleType.GUEST));
        roleRepository.save(new Role(RoleType.STUDENT));
        roleRepository.save(new Role(RoleType.TEACHER));
        roleRepository.save(new Role(RoleType.ADMIN));
    }
}
