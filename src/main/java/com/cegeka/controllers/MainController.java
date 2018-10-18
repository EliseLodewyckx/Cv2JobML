package com.cegeka.controllers;

import com.cegeka.clients.UploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(MainController.BASE_URL)
@CrossOrigin
public class MainController {

    public static final String BASE_URL="/uploadcv";

    @Autowired
    private UploadClient client;
    @PostMapping(value="/upload", consumes = "multipart/form-data")
    public String uploadSingleFile(@RequestParam(value = "file") MultipartFile file) {
        return client.uploadSingleFile(file);
    }


}
