package com.cegeka.entities;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "uploads")
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String uploadPath;
    private Timestamp timestamp = new Timestamp(new Date().getTime());

    public Upload() {
    }

    public Upload(int id, String upload) {
        this.id=id;
        this.uploadPath = upload;
        this.timestamp = new Timestamp(new Date().getTime());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpload() {
        return uploadPath;
    }

    public void setUpload(String upload) {
        this.uploadPath = upload;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
