package com.example.usermanagement.Controller;

import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Repository.IUserRepository;
import com.example.usermanagement.Security.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "UserController")
@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private JwtService jwtService;
    private IUserRepository userRepository;

    @Autowired
    public UserController(JwtService jwtService, IUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @ApiOperation(value = "GetSelf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK!, Succesfull")
    })
    @GetMapping("users/me")
    public ResponseEntity<?> GetSelf() {
        String requestToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long userid = jwtService.getUserIdFromToken(requestToken);

        User user = userRepository.getOne(userid);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
