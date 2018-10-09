package com.cegeka.services;

import com.cegeka.entities.Upload;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

@Configurable
public interface IUploadService {
    Upload getUpload(int id);
    List<Upload> getAllUpload();
    void addUpload(Upload upload);
    void delete(int id);
}
