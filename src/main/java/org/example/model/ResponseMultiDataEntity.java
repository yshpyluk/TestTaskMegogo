package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseMultiDataEntity<T> {
    private String result;
    private List<T> data;
}
