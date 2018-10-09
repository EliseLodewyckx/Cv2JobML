package com.cegeka.builder;

import com.cegeka.entities.Training;

import java.sql.Date;

public class TrainingBuilder {
    private int id;
    private String machineVersion;
    private Date lastTrained;

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

    public TrainingBuilder withLastTrained(Date lastTrained) {
        this.lastTrained=lastTrained;
        return this;
    }
}
