package com.cegeka.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="trainings")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String accuracy;
    private Timestamp lastTrained = new Timestamp(new Date().getTime());

    public Training() {
    }

    public Training(int id, String accuracy, Timestamp lastTrained) {
        this.id=id;
        this.accuracy=accuracy;
        this.lastTrained=lastTrained;
    }

    public Training(String accuracy) {
        this.accuracy = accuracy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public Timestamp getLastTrained() {
        return lastTrained;
    }

    public void setLastTrained(Timestamp lastTrained) {
        this.lastTrained = lastTrained;
    }
}
