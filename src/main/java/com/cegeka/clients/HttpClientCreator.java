package com.cegeka.clients;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpClientCreator {
    private DefaultHttpClient httpClient;

    public HttpClientCreator() {
        this.httpClient = new DefaultHttpClient();
    }

    public String useStringGetHttpClient(String urlBase) {
        String content="";
        try {
            HttpResponse response = httpClient.execute(new HttpGet(urlBase));
            ResponseHandler<String> handler = new BasicResponseHandler();
            content = handler.handleResponse(response);
            httpClient.getConnectionManager().shutdown();
            httpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return content;
    }
}
