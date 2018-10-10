package com.cegeka.controllers;

import com.cegeka.entities.Training;
import com.cegeka.services.ITrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(TrainingController.BASE_URL)
public class TrainingController {
    public static final String BASE_URL="/api/trainings";

    @Autowired
    private ITrainingService service;

    @GetMapping(value = "/get/{id}", produces = "application/json;utf-8")
    public ResponseEntity<Training> getTraining(@PathVariable("id") int id) {
        HttpStatus status = HttpStatus.OK;
        Training training = service.getTraining(id);

        if (training==null) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(training, status);
    }

    @GetMapping(value="/all", produces = "application/json;utf-8")
    public List<Training> getAll() {
        return service.getAllTrainings();
    }

    @PostMapping(value="/add", consumes = "application/json;utf-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addTraining(@RequestBody Training training) {
        service.addTraining(training);
    }

    @DeleteMapping(value="/delete/{id}")

    public void deleteMessage(@PathVariable("id") int id) {
        service.deleteTraining(id);
    }

}
