package com.cegeka.services;

import com.cegeka.entities.Training;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

@Configurable
public interface ITrainingService {
    List<Training> getAllTrainings();
    Training getTraining(int id);
    void addTraining(Training training);
    void deleteTraining(int id);
}
