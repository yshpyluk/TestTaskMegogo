package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.Request;
import org.example.model.ResponseEntity;
import org.example.model.TimeEntity;

public class TimeClient extends BaseClient {

    public ResponseEntity<TimeEntity> getTime() {
        Request request = new Request.Builder()
                .url(baseUrl + "/time")
                .build();

        String jsonString = getRequestAsString(request);
        ResponseEntity<TimeEntity> response;
        try {
            response = objectMapper.readValue(
                    jsonString,
                    objectMapper.getTypeFactory().constructParametricType(ResponseEntity.class, TimeEntity.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Fail to map json to object", e);
        }
        return response;
    }
}
