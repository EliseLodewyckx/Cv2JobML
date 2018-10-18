package com.cegeka.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.lang.math.RandomUtils.nextInt;

@Component
public class UploadClient {
    @Autowired
    private PythonApiFetchClient client;

    public String uploadSingleFile(MultipartFile file) {

        String prediction = "";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                prediction = client.predict(new String(bytes, StandardCharsets.UTF_8));

            } catch (Exception e) {
                prediction = "Error. Your CV could not be parsed or the model is down. Reason: " + e.getMessage();
            }
        }
        return prediction;
    }
}
