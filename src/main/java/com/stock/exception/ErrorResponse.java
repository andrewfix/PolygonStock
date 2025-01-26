package com.stock.exception;

import java.time.LocalDateTime;

public interface ErrorResponse {
    int getStatus();
    String getError();
    String getMessage();
    String getPath();
    LocalDateTime getTimestamp();
}