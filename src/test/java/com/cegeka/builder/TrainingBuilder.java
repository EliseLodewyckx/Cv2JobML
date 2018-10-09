package com.cegeka.builder;

import com.cegeka.entities.Training;

import java.sql.Time;
import java.util.Date;
import java.sql.Timestamp;

public class TrainingBuilder {
    private int id;
    private String machineVersion;
    private Timestamp lastTrained = new Timestamp(new Date().getTime());

    public static TrainingBuilder aTraining() { return new TrainingBuilder();}

    public Training build() {return new Training(id, machineVersion, lastTrained);}

    public TrainingBuilder withId(int id) {
        this.id=id;
        return this;
    }

    public TrainingBuilder withMachineVersion(String machineVersion) {
        this.machineVersion=machineVersion;
        return this;
    }

    public TrainingBuilder withLastTrained(Timestamp lastTrained) {
        this.lastTrained=lastTrained;
        return this;
    }
}
