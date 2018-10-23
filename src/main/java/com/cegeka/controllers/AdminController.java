package com.cegeka.controllers;

import com.cegeka.clients.PythonApiFetchClient;
import com.cegeka.entities.Training;
import com.cegeka.entities.User;
import com.cegeka.services.ILogService;
import com.cegeka.services.ITrainingService;
import com.cegeka.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

    public static final String BASE_URL = "/admin";

    @Autowired
    private PythonApiFetchClient client;
    @Autowired
    private ILogService logService;
    @Autowired
    private ITrainingService trainingService;
    @Autowired
    private IUserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping(value = "/train")
    public ModelAndView trainModelNew() {
        ModelAndView model = new ModelAndView();
        String s = client.trainModel();
        model.addObject("accuracy", s);
        model.setViewName("accuracy");
        trainingService.addTraining(new Training(s));
        return model;
    }
    @GetMapping(value="/logs")
    public ModelAndView allLogs() {
        ModelAndView model = new ModelAndView();
        model.addObject("logs", logService.All());
        model.setViewName("admin/logs");
        return model;
    }
    @GetMapping(value="/trainings")
    public ModelAndView allTrainings() {
        ModelAndView model = new ModelAndView();
        model.addObject("trainings", trainingService.getAllTrainings());
        model.setViewName("admin/trainings");
        return model;
    }

    @PostMapping(value="/createUser", consumes = "application/x-www-form-urlencoded")
    public ModelAndView addUser(@RequestParam("username")String username, @RequestParam("password") String password, @RequestParam("email")String email, @RequestParam("phone")String phone
                               ) {
        User user = new User(username, password, email, phone);
        ModelAndView model = new ModelAndView();
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            userService.addUser(user);
            model.addObject("user", user);
            model.setViewName("admin/addUserSuccess");
            return model;
        } catch (Exception ex) {

        }
        return model;

    }
    @GetMapping(value="/manageUsers")
    public ModelAndView manageUsers() {

        return getModelForUsers();
    }

    @GetMapping(value = "/deleteUser/{id}")
    public ModelAndView deleteUser(@PathVariable("id") String id) {
        userService.delete(id);

        return getModelForUsers();
    }

    @GetMapping(value = "/changeStatusUser/{id}")
    public ModelAndView changeStatusUser(@PathVariable("id")String id) {
        userService.changeStatusUser(id);
        return getModelForUsers();
    }

    private ModelAndView getModelForUsers() {
        ModelAndView model = new ModelAndView();
        model.addObject("users", getUsersHiddenPassword());
        model.setViewName("admin/manageUsers");
        return model;
    }

    private List<User> getUsersHiddenPassword() {
        List<User> users  = userService.getAllUsers();
        for (User u : users) {
            u.setPassword("HIDDEN");
        }
        return users;
    }

}
