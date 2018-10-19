package com.cegeka.controllers;

import com.cegeka.clients.UploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(MainController.BASE_URL)
@CrossOrigin
public class MainController {

    public static final String BASE_URL="/uploadcv";

    @Autowired
    private UploadClient client;
    @PostMapping(value="/upload", consumes = "multipart/form-data")
    public ModelAndView uploadSingleFile(@RequestParam(value = "file") MultipartFile file) {
        ModelAndView model = new ModelAndView();
        model.addObject("prediction", client.uploadSingleFile(file));
        model.setViewName("prediction");
        return model;
    }


}
