package com.cegeka.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.commons.lang.math.RandomUtils.nextInt;

@Component
public class UploadClient {
    public static final String UPLOADED_FOLDER="C:\\cvs\\";

    public String uploadSingleFile(MultipartFile file) {

        String status = "";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File dir = new File(UPLOADED_FOLDER);
                if (!doesFileDirExist(dir))
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

    private void writeFile(File file, byte[] bytes) throws IOException, NullPointerException {
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(
                    new FileOutputStream(file));
            outputStream.write(bytes);

        } catch (Exception ex) {

        } finally {
            outputStream.close();
        }

    }

    public boolean doesFileDirExist(File file) {
        return file.exists();
    }

    public void nextBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; )
            for (int rnd = nextInt(), n = Math.min(bytes.length - i, 4);
                 n-- > 0; rnd >>= 8)
                bytes[i++] = (byte)rnd;
    }


    public String predictJob() {
        return "";
    }

}
