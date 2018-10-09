package com.cegeka.services;

import com.cegeka.entities.logging.Log;
import com.cegeka.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService implements ILogService {
    @Autowired
    private LogRepository repo;
    @Override
    public Log find(int id) {
        return repo.findOne(id);
    }

    @Override
    public List<Log> All() {
        return repo.findAll();
    }

    @Override
    public void persist(Log l) {
        repo.save(l);
    }

    @Override
    public void update(Log l) {
        repo.save(l);
    }

    @Override
    public void delete(int id) {
        repo.delete(id);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }
}
