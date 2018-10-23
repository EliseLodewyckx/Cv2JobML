package com.cegeka.controllers;

import com.cegeka.entities.User;
import com.cegeka.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(UserController.BASE_URL)
public class UserController {
    public static final String BASE_URL="/api/users";
    @Autowired
    private IUserService service;



    @GetMapping(value = "/id/{id}", produces="application/json;charset=utf-8")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        HttpStatus status = HttpStatus.OK;
        User user = service.getUser(id);
        if (user==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, status);
    }

    @GetMapping(value = "/all",produces="application/json;charset=utf-8")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping(value = "/add", consumes="application/json;charset=utf-8")
    public void addUser(User user) {
        service.addUser(user);
    }
    @PutMapping(value = "/update", consumes = "application/json;charset=utf-8")
    public void updateUser(User user) {
        service.updateUser(user);
    }

    @DeleteMapping(value = "/delete/{id}", consumes = "application/json;charset=utf-8")
    public void deleteUser(@PathVariable("id")String id) {
        service.delete(id);
    }
}
