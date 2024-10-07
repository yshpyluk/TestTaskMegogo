package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.example.model.ResponseMultiDataEntity;
import org.example.model.channel.ChannelEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ChannelClient extends BaseClient {

    public ResponseMultiDataEntity<ChannelEntity> getChannel(List<Integer> channelIds) {
        String channelIds_string = channelIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        HttpUrl url = HttpUrl.parse(baseUrl + "/channel").newBuilder()
                .addQueryParameter("video_ids", channelIds_string)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .build();

        String jsonString = getRequestAsString(request);
        ResponseMultiDataEntity<ChannelEntity> response;
        try {
            response = objectMapper.readValue(
                    jsonString,
                    objectMapper.getTypeFactory().constructParametricType(ResponseMultiDataEntity.class, ChannelEntity.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Fail to map json to object", e);
        }
        return response;
    }
}
