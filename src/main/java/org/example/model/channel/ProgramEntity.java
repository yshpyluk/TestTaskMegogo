package org.example.model.channel;

import lombok.Data;

import java.util.Map;

@Data
public class ProgramEntity {
    private int id;
    private int external_id;
    private String title;
    private CategoryEntity category;
    private String season;
    private String episode;
    private Map<String, String> pictures;
    private long start_timestamp;
    private long end_timestamp;
    private String start;
    private String end;
    private String virtual_object_id;
    private String schedule_type;
}
