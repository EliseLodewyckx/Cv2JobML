package com.cegeka.clients;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class TrainerClient {

    public void trainModel() throws IOException, InterruptedException {
        int seed = 123;
        double learningRate = 0.01;
        int batchSize = 50;
        int nEpochs = 30;
        int numInputs=25;
        int numOutputs=25;
        int numHiddenNodes = 20;

        // load the training data
        RecordReader rr = new CSVRecordReader();
        rr.initialize(new FileSplit(new File("d")));



    }
}
