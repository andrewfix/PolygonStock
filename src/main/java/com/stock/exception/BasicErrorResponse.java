package com.stock.exception;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class BasicErrorResponse implements ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
