package com.cegeka.controllers;

import com.cegeka.clients.PythonApiFetchClient;
import com.cegeka.entities.Training;
import com.cegeka.services.ILogService;
import com.cegeka.services.ITrainingService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ILogService logService;
    @Autowired
    private ITrainingService trainingService;


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

}
