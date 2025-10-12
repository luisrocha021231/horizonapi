package com.horizon.api.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.horizon.api.entitites.UserEntity;
import com.horizon.api.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // method to create a new user
    public void createUser(UserEntity userEntity) {

        String hashedPassword = passwordEncoder.encode(userEntity.getPassword());

        UserEntity newUser = new UserEntity();
        
        newUser.setUsername(userEntity.getUsername());
        newUser.setPassword(hashedPassword);
        newUser.setEmail(userEntity.getEmail());
        newUser.setRole(userEntity.getRole());

        userRepository.save(newUser);
    }

    // method to check if a user exists by username
    public boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // method to load user by username for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().toString());

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority));
    }
}
