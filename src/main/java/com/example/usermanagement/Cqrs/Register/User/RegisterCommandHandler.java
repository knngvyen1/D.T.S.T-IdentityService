package com.example.usermanagement.Cqrs.Register.User;

import com.example.usermanagement.Cqrs.Register.RegisterBackend.SaveUserDTO;
import com.example.usermanagement.Domain.Model.RoleType;
import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Exception.BadRequestException;
import com.example.usermanagement.Repository.IUserRepository;
import com.example.usermanagement.Repository.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class RegisterCommandHandler {
    private IUserRepository userRepository;
    private IUserRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public RegisterCommandHandler(IUserRepository userRepository, IUserRoleRepository roleRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User handle(RegisterCommandDTO command)
    {

        if(userRepository.findByEmail(command.getEmail()).isPresent())
        {
            throw new BadRequestException("User already exist");
        }
        User user = new User();
        user.setEmail(command.getEmail());
        user.setName(command.getName());
        user.setPassword(passwordEncoder.encode(command.getPassword()));
        user.setRole(roleRepository.getByRoleType(RoleType.STUDENT));

        User storeduser = userRepository.save(user);

        String url = "http://localhost:7070/users";
        // create headers
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);

        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        SaveUserDTO dto = new SaveUserDTO();
        dto.setUserID(storeduser.getId());
        dto.setEmail(storeduser.getEmail());
        dto.setName(storeduser.getName());
        dto.setRoleType(storeduser.getRole().getRoleType());
        HttpEntity<SaveUserDTO> request = new HttpEntity<>(dto, headers);
        // send POST request
        ResponseEntity<User> response = this.restTemplate.postForEntity(url, request, User.class);
        return storeduser;
    }
}
