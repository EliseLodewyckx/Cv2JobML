package com.cegeka.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="trainings")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String machineVersion;
    private Date lastTrained;

    public Training() {
    }

    public Training(int id, String machineVersion, Date lastTrained) {
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

    public Date getLastTrained() {
        return lastTrained;
    }

    public void setLastTrained(Date lastTrained) {
        this.lastTrained = lastTrained;
    }
}
