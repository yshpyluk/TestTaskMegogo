package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.PropertyReader;

import java.io.IOException;

public abstract class BaseClient {

    protected String baseUrl;

    protected ObjectMapper objectMapper;

    private final OkHttpClient client;

    public BaseClient() {
        baseUrl = new PropertyReader().getProperties().getBaseUrl();
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }

    protected String getRequestAsString(Request request) {
        Response response = getRequest(request);
        return extractResponseBodyAsString(response);
    }

    protected Response getRequest(Request request) {
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException("Fail to perform GET request to: " + request.url());
        }

        return response;
    }

    private String extractResponseBodyAsString(Response response) {
        String responseBody;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException("Fail to extract body as string from response");
        }

        return responseBody;
    }
}
