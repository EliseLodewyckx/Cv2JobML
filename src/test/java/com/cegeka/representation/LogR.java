package com.cegeka.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class LogR {

    private int id;
    private String message;
    private Timestamp time;

    public static LogR of(int id, String message, Timestamp time) {
        return new LogR(id, message, time);
    }

    @JsonCreator
    private LogR(@JsonProperty("id") int id, @JsonProperty("message") String message, @JsonProperty("time") Timestamp time) {
        this.id = id;
        this.message = message;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTime() {
        return time;
    }

    public String toString() {
        return "LogR{" +
                "time='" + time + '\'' +
                ", message='" + message + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}