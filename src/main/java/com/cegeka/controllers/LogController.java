package com.cegeka.controllers;

import com.cegeka.entities.logging.JMSMessageLogger;
import com.cegeka.entities.logging.Log;
import com.cegeka.services.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(LogController.BASE_URL)
public class LogController {
    public static final String BASE_URL = "/api/logs";

    @Autowired
    private ILogService service;

    @Autowired
    JMSMessageLogger jms;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces="application/json;utf-8")
    public List<Log> getAllLogs() {
        return service.All();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;utf-8")
    public ResponseEntity<Log> getLog(@PathVariable("id") int id) {
        HttpStatus status = HttpStatus.OK;

        Log log = service.find(id);

        if (log==null) {
            status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(log, status);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json;utf-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addLog(@RequestBody Log log) {
        service.persist(log);
    }

    @RequestMapping(value = "/delete/{id)", method = RequestMethod.DELETE)
    public void deleteLog(@PathVariable("id") int id) {
        service.delete(id);
    }

    @RequestMapping(value="/clear", method = RequestMethod.DELETE)
    public void deleteAllLogs() {
        service.deleteAll();
    }
}
