package com.worstmovie.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CustomErrorResponse {

    private String message;

    private String timeStamp;

    public CustomErrorResponse(String message) {
        this.message = message;
        this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}

