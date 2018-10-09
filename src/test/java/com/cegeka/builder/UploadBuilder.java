package com.cegeka.builder;

import com.cegeka.entities.Upload;

import java.sql.Timestamp;

public class UploadBuilder {
    private int id;
    private String upload;


    public static UploadBuilder aUpload() { return new UploadBuilder();}

    public Upload build() { return new Upload(id, upload);}

    public UploadBuilder withId(int id) {
        this.id=id;
        return this;
    }

    public UploadBuilder withUpload(String upload) {
        this.upload=upload;
        return this;
    }


}
