package com.cegeka.controllers;

import com.cegeka.entities.user.User;
import com.cegeka.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(UserController.BASE_URL)
public class UserController {
    public static final String BASE_URL = "/api/users";
    PasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private IUserService service;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;utf-8")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        HttpStatus status = HttpStatus.OK;

        User user = service.getUser(id);

        if (user==null) {
            status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<User>(status);
        }
        return new ResponseEntity<>(user, status);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;utf-8")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json;utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        service.addUser(user);
    }

    @RequestMapping(value="/login", method = RequestMethod.GET, produces = "application/json;charset:utf-8")
    @ResponseBody
    public ResponseEntity<User> validateUser(@RequestParam(value="username") String username, @RequestParam(value = "password") String rawPassword) {
        HttpStatus status = HttpStatus.OK;
        User user = service.getUser(username);
        if (user == null) {
            return new ResponseEntity<>(new User(), status);
        } else {
            if (encoder.matches(rawPassword, user.getPassword())) {
                String accesstoken = UUID.randomUUID().toString();
                user.setAccessToken(accesstoken);
                service.updateUser(user);
                user.setPassword("HIDDEN");
                return new ResponseEntity<>(user, status);
            } else {
                return new ResponseEntity<>(new User(), status);
            }
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id")String id) {
        service.deleteUser(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json;utf-8")
    public void updateUser(@RequestBody User user) {
        service.updateUser(user);
    }
    @RequestMapping(value="/logout", method = RequestMethod.GET, produces = "application/json;charset:utf-8")
    @ResponseBody
    public boolean logoutUser(@RequestParam(value="username") String username) {
        User user = service.getUser(username);
        user.setAccessToken("");
        service.updateUser(user);
        return true;
    }

}
