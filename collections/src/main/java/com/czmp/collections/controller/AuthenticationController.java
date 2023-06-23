package com.czmp.collections.controller;

import com.czmp.collections.dto.AuthResponseDTO;
import com.czmp.collections.dto.CredentialsDTO;
import com.czmp.collections.dto.MessageResponseDTO;
import com.czmp.collections.model.Role;
import com.czmp.collections.repository.EndUserRepository;
import com.czmp.collections.repository.RoleRepository;
import com.czmp.collections.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.czmp.collections.model.EndUser;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EndUserRepository endUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody CredentialsDTO credentialsDTO){
        if(endUserRepository.existsByUsername(credentialsDTO.getUsername())){
            return new ResponseEntity<>(new MessageResponseDTO("Username already taken!"), HttpStatus.BAD_REQUEST);
        }

        EndUser endUser = new EndUser();
        endUser.setUsername(credentialsDTO.getUsername());
        endUser.setPassword(passwordEncoder.encode(credentialsDTO.getPassword()));
        endUser.setLastSeenNotification(new Date());
        if(roleRepository.findByName("USER").isPresent()) {
            endUser.setRoles(List.of(roleRepository.findByName("USER").get()));
        } else {
            return new ResponseEntity<>(new MessageResponseDTO("There is no USER role in database therefore a user could not be created"), HttpStatus.BAD_REQUEST);
        }
        endUserRepository.save(endUser);
        return new ResponseEntity<>(new MessageResponseDTO("Successfully added user"), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody CredentialsDTO credentialsDTO){
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentialsDTO.getUsername(), credentialsDTO.getPassword())
            );
        } catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO("Username and password don't match"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
}
