package com.cegeka.services;

import com.cegeka.entities.Upload;
import com.cegeka.repositories.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadService implements IUploadService {
    @Autowired
    private UploadRepository repo;
    @Override
    public Upload getUpload(int id) {
        return repo.findOne(id);
    }

    @Override
    public List<Upload> getAllUpload() {
        return repo.findAll();
    }

    @Override
    public void addUpload(Upload upload) {
        repo.save(upload);
    }

    @Override
    public void delete(int id) {
        repo.delete(id);
    }
}
