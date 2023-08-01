package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.dao.RoleDao;
import com.ecom.entity.Role;
import com.ecom.entity.User;
import com.ecom.service.UserService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleDao roleDao;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
    @PostMapping("/admin/add")
    public ResponseEntity<String> addAdmin(@RequestBody User user) {
    	System.out.println("Registering new admin: " + user.toString());
    	// Fetch the "Admin" role from the database
        Role adminRole = roleDao.findById("Admin")
            .orElseThrow(() -> new RuntimeException("Admin role not found"));

        // Assign the "Admin" role to the user
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        userService.registerNewAdmin(user);
        return ResponseEntity.ok("Admin added successfully!");
    }
}
