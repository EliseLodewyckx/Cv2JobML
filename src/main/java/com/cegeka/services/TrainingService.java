package com.cegeka.services;

import com.cegeka.entities.Training;
import com.cegeka.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService implements ITrainingService {
    @Autowired
    private TrainingRepository repo;
    @Override
    public List<Training> getAllTrainings() {
        return repo.findAll();
    }

    @Override
    public Training getTraining(int id) {
        return repo.findOne(id);
    }

    @Override
    public void addTraining(Training training) {
        repo.save(training);
    }

    @Override
    public void deleteTraining(int id) {
        repo.delete(id);
    }
}
