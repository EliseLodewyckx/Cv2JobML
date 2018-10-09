package com.cegeka.controllers;

import com.cegeka.clients.UploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping(UploadController.BASE_URL)
public class UploadController {
    public static final String BASE_URL = "/api/upload";

    @Autowired
    private UploadClient client;

    @RequestMapping(value="/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String uploadSingleFile(@RequestParam(value = "file") MultipartFile file) {
        return client.uploadSingleFile(file);
    }
}
