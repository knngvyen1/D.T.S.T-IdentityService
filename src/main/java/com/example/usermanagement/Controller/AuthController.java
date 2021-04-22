package com.example.usermanagement.Controller;

import com.example.usermanagement.Cqrs.Login.LoginCommandDTO;
import com.example.usermanagement.Cqrs.Login.LoginCommandHandler;
import com.example.usermanagement.Cqrs.Login.LoginCommandResult;
import com.example.usermanagement.Cqrs.Refresh.RefreshCommandDTO;
import com.example.usermanagement.Cqrs.Refresh.RefreshCommandHandler;
import com.example.usermanagement.Cqrs.Refresh.RefreshCommandResult;
import com.example.usermanagement.Cqrs.Register.User.RegisterCommandDTO;
import com.example.usermanagement.Cqrs.Register.User.RegisterCommandHandler;
import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Domain.Services.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Api(tags = "AutController")
@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    RestTemplate restTemplate = new RestTemplate();

    private RegisterCommandHandler registerCommandHandler;
    private LoginCommandHandler loginCommandHandler;
    private RefreshCommandHandler refreshCommandHandler;
    private AdminService adminService;

    @Autowired
    public AuthController(RegisterCommandHandler registerCommandHandler, LoginCommandHandler loginCommandHandler, RefreshCommandHandler refreshCommandHandler, AdminService adminService) {
        this.registerCommandHandler = registerCommandHandler;
        this.loginCommandHandler = loginCommandHandler;
        this.refreshCommandHandler = refreshCommandHandler;
        this.adminService = adminService;
    }

    @ApiOperation(value = "Register")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK!, Succesfull")
    })
    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestBody RegisterCommandDTO command) {
        User result = registerCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginCommandDTO command) {
        LoginCommandResult result = loginCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshCommandDTO command) {
        RefreshCommandResult result = refreshCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
