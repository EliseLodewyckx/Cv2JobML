package com.cegeka.controllers;

import com.cegeka.clients.UploadClient;
import com.cegeka.entities.Upload;
import com.cegeka.services.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(UploadController.BASE_URL)
public class UploadController {
    public static final String BASE_URL = "/api/upload";

    @Autowired
    private IUploadService service;

     @GetMapping(value = "/get/{id}", produces = "application/json;utf-8")
     public ResponseEntity<Upload> getUpload(@PathVariable("id") int id) {
        HttpStatus status = HttpStatus.OK;
        Upload upload = service.getUpload(id);

        if (upload==null) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(upload, status);
    }

    @GetMapping(value="/all", produces = "application/json;utf-8")
    public List<Upload> getAll() {
        return service.getAllUpload();
    }

    @PostMapping(value="/add", consumes = "application/json;utf-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUpload(@RequestBody Upload upload) {
        service.addUpload(upload);
    }

    @DeleteMapping(value="/delete/{id}")

    public void deleteMessage(@PathVariable("id") int id) {
        service.delete(id);
    }

}
