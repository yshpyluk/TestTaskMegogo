package org.example.model.channel;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChannelEntity {
    private int id;
    private int external_id;
    private String title;
    private Map<String, String> pictures;
    private int video_id;
    private List<ProgramEntity> programs;
}
