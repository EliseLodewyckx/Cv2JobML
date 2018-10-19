package com.cegeka.controllers;

import com.cegeka.clients.PythonApiFetchClient;
import com.cegeka.services.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

    public static final String BASE_URL = "/admin";

    @Autowired
    private PythonApiFetchClient client;
    @Autowired
    private ILogService service;

    @GetMapping(value = "/train")
    public ModelAndView trainModelNew() {
        ModelAndView model = new ModelAndView();
        model.addObject("accuracy", client.trainModel());
        model.setViewName("accuracy");
        return model;
    }
    @GetMapping(value="/logs")
    public ModelAndView allLogs() {
        ModelAndView model = new ModelAndView();
        model.addObject("logs", service.All());
        model.setViewName("admin/logs");
        return model;
    }

}
