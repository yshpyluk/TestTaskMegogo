package org.example.model;

import lombok.Data;

@Data
public class ResponseEntity<T> {
    private String result;
    private T data;
}
