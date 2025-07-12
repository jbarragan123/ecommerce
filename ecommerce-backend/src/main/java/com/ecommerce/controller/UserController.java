package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AuditService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final AuditService auditService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, AuditService auditService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.auditService = auditService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        auditService.logAction("listed all users", "User", null);
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        auditService.logAction("viewed user " + id, "User", id);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepository.findById(id).orElseThrow();
        existingUser.setEmail(user.getEmail());
        existingUser.setFrequentClient(user.isFrequentClient());
        User updated = userRepository.save(existingUser);
        auditService.logAction("updated user " + id, "User", id);
        return updated;
    }

    // @DeleteMapping("/{id}")
    // public void deleteUser(@PathVariable Long id) {
    //     userRepository.deleteById(id);
    //     auditService.logAction("deleted user " + id, "User", id);
    // }

    @PatchMapping("/{id}/inactivate")
    public User inactivateUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(false);
        User updated = userRepository.save(user);
        auditService.logAction("inactivated user " + id, "User", id);
        return updated;
    }
    
}
