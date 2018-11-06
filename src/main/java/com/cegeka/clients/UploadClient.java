package com.cegeka.clients;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.lang.math.RandomUtils.nextInt;

@Component
public class UploadClient {
    @Autowired
    private PythonApiFetchClient client;

    public String uploadSingleFile(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String prediction = "";
        if (!file.isEmpty()) {
            try {

                if (!extension.equals("pdf")) {
                    byte[] bytes = file.getBytes();
                    prediction = client.predict(new String(bytes, StandardCharsets.UTF_8));
                } else {
                    InputStream stream = new ByteArrayInputStream(file.getBytes());
                    PDDocument pdd = PDDocument.load(stream);
                    PDFTextStripper pdfStripper = new PDFTextStripper();
                    String text = pdfStripper.getText(pdd);
                    text = text.replaceAll("[^a-zA-Z0-9\\s+]", "");
                    text = text.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
                    prediction = client.predict(text);


                }
            } catch (Exception e) {
                prediction = "Error. Your CV could not be parsed or the model is down. Please make sure your PDF is not an image. ";
                e.getStackTrace();
                System.out.print(e.getMessage());
            }

        }
        return prediction;
    }
}
