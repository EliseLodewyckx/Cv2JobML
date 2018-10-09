package com.cegeka.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class TrainingR {
    private int id;
    private String machineVersion;
    private Date lastTrained;

    public static TrainingR of(int id, String machineVersion, Date lastTrained) {return new TrainingR(id, machineVersion, lastTrained);}

    @JsonCreator
    private TrainingR(@JsonProperty("id") int id, @JsonProperty("machineVersion") String machineVersion, @JsonProperty("lastTrained") Date lastTrained) {
        this.id=id;

        this.machineVersion=machineVersion;
        this.lastTrained=lastTrained;
    }

    public int getId() {
        return id;
    }

    public String getMachineVersion() {
        return machineVersion;
    }

    public Date getlastTrained() {
        return lastTrained;
    }

    public String toString() {
        return "TrainingR{" +
                "id='" + id + '\'' +
                ", lastTrained='" + lastTrained + '\'' +
                ", machineVersion='" + machineVersion + '\'' +
                '}';
    }
}
