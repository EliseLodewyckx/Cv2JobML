package com.cegeka.clients;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PythonApiFetchClient {

    @Autowired
    HttpClientCreator client;
    public String trainModel() {

        final String urlBase = "http://localhost:5000/scikit/api/train";

        return client.useStringGetHttpClient(urlBase);
    }

    public String predict(String predictThis) {
        String text = cleanData(predictThis);
        final String urlBase = "http://localhost:5000/scikit/api/predict/" + text;
        return client.useStringGetHttpClient(urlBase);
    }

    public String cleanData(String text) {
        text = StringUtils.normalizeSpace(text);
        text = text.replaceAll("\\s","%20");

        return text;
    }

}
