package com.czmp.collections.service;

import com.czmp.collections.dto.UserLoginDTO;
import com.czmp.collections.dto.UserRegisterDTO;
import com.czmp.collections.model.Product;
import com.czmp.collections.model.Role;
import com.czmp.collections.model.User;
import com.czmp.collections.repository.UserRepository;
import com.czmp.collections.service.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()){
            return user.get();
        }
        throw new UsernameNotFoundException(username);
    }
    public List<User> get() {
            return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    public ResponseEntity<?> login(UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findUserByUsername(userLoginDTO.getUsername());
        if(user.isPresent()){
            if (passwordEncoder.matches(userLoginDTO.getPassword(), user.get().getPassword())) {
                return ResponseEntity.ok().body(user.get());
            }
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<?> register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        System.out.println(passwordEncoder.encode(userRegisterDTO.getPassword()));
        System.out.println(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setUsername(userRegisterDTO.getUsername());
        try {
            userRepository.save(user);
            return ResponseEntity.ok().body(new Byte[0]);
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
