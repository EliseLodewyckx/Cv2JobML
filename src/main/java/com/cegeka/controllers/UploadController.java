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

    @Autowired
    private UploadClient client;

    @RequestMapping(value="/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String uploadSingleFile(@RequestParam(value = "file") MultipartFile file) {
        return client.uploadSingleFile(file);
    }

     @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;utf-8")
    public ResponseEntity<Upload> getUpload(@PathVariable("id") int id) {
        HttpStatus status = HttpStatus.OK;
        Upload upload = service.getUpload(id);

        if (upload==null) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<Upload>(upload, status);
    }

    @RequestMapping(value="/all", method = RequestMethod.GET, produces = "application/json;utf-8")
    public List<Upload> getAll() {
        return service.getAllUpload();
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUpload(@RequestBody Upload upload) {
        service.addUpload(upload);
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)

    public void deleteMessage(@PathVariable("id") int id) {
        service.delete(id);
    }

}
