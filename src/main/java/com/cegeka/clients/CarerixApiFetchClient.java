package com.cegeka.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarerixApiFetchClient {
    private static final String carerixToken = "";
    private static final String carerixUrl = "";

    @Autowired
    HttpClientCreator client;

    public void fetchAllEmployees() {

    }

}
