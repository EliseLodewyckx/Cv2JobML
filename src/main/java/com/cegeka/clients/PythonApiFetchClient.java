package com.cegeka.clients;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PythonApiFetchClient {

    public String trainModel() {

        final String urlBase = "http://localhost:5000/scikit/api/train";

        return useHttpClient(urlBase);
    }
    public String predict(String predictThis) {
        final String urlBase = "http://localhost:5000/scikit/api/predict/" + predictThis;
        String url = urlBase.replaceAll(" ", "%20");
        return useHttpClient(url);
    }

    public String useHttpClient(String urlBase) {
        String content="";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(urlBase));
            ResponseHandler<String> handler = new BasicResponseHandler();
            content = handler.handleResponse(response);
            httpclient.getConnectionManager().shutdown();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}
