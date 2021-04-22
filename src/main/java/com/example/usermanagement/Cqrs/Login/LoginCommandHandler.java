package com.example.usermanagement.Cqrs.Login;

import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Domain.Model.UserToken;
import com.example.usermanagement.Exception.NotFoundException;
import com.example.usermanagement.Repository.IUserRepository;
import com.example.usermanagement.Repository.IUserTokenRepository;
import com.example.usermanagement.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginCommandHandler {
    private IUserRepository userRepository;
    private IUserTokenRepository userTokenRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @Autowired
    public LoginCommandHandler(IUserRepository userRepository, IUserTokenRepository userTokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginCommandResult handle(LoginCommandDTO loginCommandDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(loginCommandDTO.getEmail());
        LoginCommandResult result = new LoginCommandResult();
        //check if user exist
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user = optionalUser.get();

        //check if password matches
        if (passwordEncoder.matches(loginCommandDTO.getPassword(), user.getPassword())) {
            Optional<UserToken> optionUserToken = userTokenRepository.getByEmail(user.getEmail());
            optionUserToken.ifPresent(userToken -> userTokenRepository.deleteById(userToken.getId()));

            //create token
            UserToken userToken = jwtService.createToken(user);

            //create en return result

            result.setAccessToken(userToken.getToken());
            result.setRefreshToken(userToken.getRefreshToken());
            return result;

        }
        if ((loginCommandDTO.getPassword() != user.getPassword())) {
            Optional<UserToken> optionUserToken = userTokenRepository.getByEmail(user.getUsername());
            optionUserToken.ifPresent(userToken -> userTokenRepository.deleteById(userToken.getId()));

            //create token
            UserToken userToken = jwtService.createToken(user);

            //create en return result
            result.setAccessToken(userToken.getToken());
            result.setRefreshToken(userToken.getRefreshToken());
            return result;

        }

        //check if user already has token, if so delete the token
        return result;
    }
}
