package com.example.security.Service;

import com.example.security.Api.ApiException;
import com.example.security.Model.User;
import com.example.security.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    public void register(User user) {

        user.setRole("CUSTOMER");

        String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashPassword);

        authRepository.save(user);
    }

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }

    public void updateUser(Integer userId, User updatedUser) {
        User user = authRepository.findUserById(userId);

        if(user == null)
            throw new ApiException("User not found");

        user.setUsername(updatedUser.getUsername());
        if(updatedUser.getPassword() != null){
            String hashPassword = new BCryptPasswordEncoder().encode(updatedUser.getPassword());
            user.setPassword(hashPassword);
        }

        authRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        User user = authRepository.findUserById(userId);

        if(user == null)
            throw new ApiException("User not found");

        authRepository.delete(user);
    }

}
