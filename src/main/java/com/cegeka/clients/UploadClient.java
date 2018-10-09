package com.cegeka.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class UploadClient {
    private static final String UPLOADED_FOLDER="C:\\cvs\\";

    public String uploadSingleFile(MultipartFile file) {

        String status = "";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File dir = new File(UPLOADED_FOLDER);
                if (!doesFileExist(dir))
                    dir.mkdirs();
                File uploadFile = new File(dir.getAbsolutePath() + File.separator +
                        file.getOriginalFilename());
               writeFile(uploadFile, bytes);


                status = status + " Successfully uploaded file=" + file.getOriginalFilename();
            } catch (Exception e) {
                status = status + "Failed to upload " + file.getOriginalFilename() + " " + e.getMessage();
            }
        }
        return status;
    }

    private void writeFile(File file, byte[] bytes) throws IOException {

        BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(file));
        outputStream.write(bytes);
        outputStream.close();
    }

    public boolean doesFileExist(File file) {
        return file.exists();
    }

    public String predictJob() {
        return "";
    }

}
