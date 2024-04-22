package com.example.security.Controller;

import com.example.security.Api.ApiResponse;
import com.example.security.Model.User;
import com.example.security.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity login(){
        return ResponseEntity.status(200).body(new ApiResponse("Welcome Back"));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(200).body(new ApiResponse("Thank you"));
    }

    // only admin can get all users
    @GetMapping("/get-all")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(200).body(authService.getAllUsers());
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(@PathVariable Integer userId, @RequestBody @Valid User user){
        authService.updateUser(userId, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    // only admin can delete a user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId){
        authService.deleteUser(userId);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }


}
