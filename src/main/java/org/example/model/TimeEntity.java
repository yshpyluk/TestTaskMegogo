package org.example.model;

import lombok.Data;

@Data
public class TimeEntity {
    private int utc_offset;
    private long timestamp_gmt;
    private long timestamp_local;
    private String timezone;
    private String time_local;
    private long timestamp;
}
