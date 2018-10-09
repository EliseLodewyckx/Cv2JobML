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
    private String machineVersion;
    private Timestamp lastTrained = new Timestamp(new Date().getTime());

    public Training() {
    }

    public Training(int id, String machineVersion, Timestamp lastTrained) {
        this.id=id;
        this.machineVersion=machineVersion;
        this.lastTrained=lastTrained;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMachineVersion() {
        return machineVersion;
    }

    public void setMachineVersion(String machineVersion) {
        this.machineVersion = machineVersion;
    }

    public Timestamp getLastTrained() {
        return lastTrained;
    }

    public void setLastTrained(Timestamp lastTrained) {
        this.lastTrained = lastTrained;
    }
}
