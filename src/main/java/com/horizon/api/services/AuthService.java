package com.horizon.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.horizon.api.dtos.LoginUserDTO;
import com.horizon.api.dtos.NewUserDTO;
import com.horizon.api.entitites.RoleEntity;
import com.horizon.api.entitites.UserEntity;
import com.horizon.api.enums.RoleList;
import com.horizon.api.jwt.JwtUtil;
import com.horizon.api.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Method to authenticate user and generate JWT token
    public String authenticateUser(LoginUserDTO loginUserDto) {
        
        // Authenticate user credentials
        Authentication authResult = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authResult);

        return jwtUtil.generateToken(authResult);
    }

    // Method to register a new user
    public void registerUser(NewUserDTO newUserDto) {
        if(userService.userExistsByUsername(newUserDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already taken.");
        }

        // Assign default role "VIEWER" to new users
        RoleEntity roleUser = roleRepository.findByName(RoleList.VIEWER)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not founded."));
        
        // Create and save new user
        UserEntity user = new UserEntity();
        user.setUsername(newUserDto.getUsername());
        user.setPassword(newUserDto.getPassword());
        user.setEmail(newUserDto.getEmail());
        user.setRole(roleUser);
        userService.createUser(user);
    }
}
