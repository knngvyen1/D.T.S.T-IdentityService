package com.example.usermanagement.Controller;

import com.example.usermanagement.Cqrs.Login.LoginCommandHandler;
import com.example.usermanagement.Cqrs.Refresh.RefreshCommandHandler;
import com.example.usermanagement.Cqrs.Register.Teacher.GetTeacherCommandHandler;
import com.example.usermanagement.Cqrs.Register.Teacher.RegisterTeacherCommandHandler;
import com.example.usermanagement.Cqrs.Register.Teacher.TeacherCommandDTO;
import com.example.usermanagement.Cqrs.Register.User.RegisterCommandDTO;
import com.example.usermanagement.Domain.Model.User;
import com.example.usermanagement.Domain.Services.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Admin Controller")
@RestController
@CrossOrigin(origins = "*")
public class AdminController {

    private RegisterTeacherCommandHandler RegisterTeacherCommandHandler;
    private GetTeacherCommandHandler GetTeacherCommandHandler;
    private AdminService adminService;

    @PostMapping("/api/users/health")
    public String healthCheck() {
        return "Healhy";
    }


    @Autowired
    public AdminController(RegisterTeacherCommandHandler RegisterTeacherCommandHandler, GetTeacherCommandHandler GetTeacherCommandHandler, AdminService adminService) {
        this.RegisterTeacherCommandHandler = RegisterTeacherCommandHandler;
        this.adminService = adminService;
        this.GetTeacherCommandHandler = GetTeacherCommandHandler;
    }

    @ApiOperation(value = "Add Teacher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK!, Succesfull")
    })
    @PostMapping("/api/auth/registerTeacher")
    public ResponseEntity<User> addTeacher(@RequestBody RegisterCommandDTO command) {
        User result = RegisterTeacherCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @ApiOperation(value = "Get Teacher by ID")
    @GetMapping("/api/teachers/{userID}")
    public ResponseEntity<User> getTeacher(@PathVariable long userID) {
        User result = GetTeacherCommandHandler.handle(userID);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping("/api/users/{userID}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUserByID(@PathVariable long userID) {
        adminService.deleteUser(userID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
