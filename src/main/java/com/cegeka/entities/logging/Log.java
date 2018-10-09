package com.cegeka.entities.logging;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Timestamp time = new Timestamp(new Date().getTime());
    @Column(length = 2056)
    private String message;

    public Log(String message) { this.message = message; }
    public Log() {}

    public int getId () {
        return id;
    }
    public Timestamp getTime () {
        return time;
    }
    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }
}
