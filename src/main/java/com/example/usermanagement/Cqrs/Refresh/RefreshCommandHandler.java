package com.example.usermanagement.Cqrs.Refresh;

import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Domain.Model.UserToken;
import com.example.usermanagement.Exception.BadRequestException;
import com.example.usermanagement.Exception.NotFoundException;
import com.example.usermanagement.Repository.IUserRepository;
import com.example.usermanagement.Repository.IUserTokenRepository;
import com.example.usermanagement.Security.JwtService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshCommandHandler {
    private IUserRepository userRepository;
    private IUserTokenRepository userTokenRepository;
    private JwtService jwtService;


    public RefreshCommandHandler(IUserRepository userRepository, IUserTokenRepository userTokenRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
        this.jwtService = jwtService;
    }

    public RefreshCommandResult handle (RefreshCommandDTO refreshCommandDTO)
    {
        //get the refresh and normal token from command
        Optional<UserToken> optionalUserToken = userTokenRepository.getByToken(refreshCommandDTO.getAccessToken());

        // check if token exist
        if(optionalUserToken.isEmpty())
        {
            throw new BadRequestException("The provided token was never used");
        }

        UserToken token = optionalUserToken.get();
        //check if jwt still valid

        if(jwtService.isTokenValid(token.getToken()))
        {
            throw new BadRequestException("The provided token is still available");
        }
        //check if jwt in the database matches the provided refresh token
        if(!token.getRefreshToken().equals(refreshCommandDTO.getRefreshToken()))
        {
            throw new BadRequestException("The token didn't match");
        }
        //remove old token in datbase

        userTokenRepository.deleteById(token.getId());

        //create and return new token
        Optional<User> optionalUser = userRepository.findByEmail(token.getEmail());

        if(optionalUser.isEmpty())
        {
            throw new NotFoundException("The use for this token is not found");
        }

        User user = optionalUser.get();
        UserToken newToken = jwtService.createToken(user);
        RefreshCommandResult result = new RefreshCommandResult();
        result.setAccessToken(newToken.getToken());
        result.setRefreshToken(newToken.getRefreshToken());
        return result;


    }
}
