package com.example.usermanagement.Domain.Services;

import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private IUserRepository userRepository;

    public AdminService(IUserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    public User deleteUser (long userID)
    {
        User founduser = getUserbyID(userID);
        userRepository.delete(founduser);
        return null;
    }

    public User getUserbyID( long userID)
    {
        User founduser = userRepository.findById(userID).get();
        return founduser;
    }
}
