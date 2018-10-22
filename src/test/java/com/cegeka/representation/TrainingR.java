package com.cegeka.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.Date;

public class TrainingR {
    private int id;
    private String accuracy;
    private Timestamp lastTrained;

    public static TrainingR of(int id, String accuracy, Timestamp lastTrained) {return new TrainingR(id, accuracy, lastTrained);}

    @JsonCreator
    private TrainingR(@JsonProperty("id") int id, @JsonProperty("accuracy") String accuracy, @JsonProperty("lastTrained") Timestamp lastTrained) {
        this.id=id;

        this.accuracy=accuracy;
        this.lastTrained=lastTrained;
    }

    public int getId() {
        return id;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public Timestamp getlastTrained() {
        return lastTrained;
    }

    public String toString() {
        return "TrainingR{" +
                "id='" + id + '\'' +
                ", lastTrained='" + lastTrained + '\'' +
                ", accuracy='" + accuracy + '\'' +
                '}';
    }
}
