package com.cegeka.clients;

import java.io.InputStream;

public class PythonClient {
    Process p;

    public String runPrediction(String text) {
        String prediction="";
        try {
            p = Runtime.getRuntime().exec("py xx.py -t \"" + text + "\"");
            InputStream is = p.getInputStream();
            int i = 0;
            while ( (i = is.read()) != -1) prediction += (char) i;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return prediction;
    }

    public boolean retrainModel() {
        try {
            p = Runtime.getRuntime().exec("py xx.py");
            return true;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return false;
    }
}
