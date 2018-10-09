package com.cegeka.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class UploadR {

    private int id;
    private String upload;
    private Timestamp timestamp;

    public static UploadR of(int id, String upload, Timestamp timestamp) {
        return new UploadR(id, upload, timestamp);
    }

    @JsonCreator
    private UploadR(@JsonProperty("id") int id, @JsonProperty("upload") String upload, @JsonProperty("timestamp") Timestamp timestamp) {
        this.id=id;
        this.upload=upload;
        this.timestamp=timestamp;
    }

    public int getId() {
        return id;
    }

    public String getUpload() {
        return upload;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


}
