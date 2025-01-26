package com.stock.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
public class ValidationErrorResponse extends BasicErrorResponse {
    private final Map<String, String> fieldErrors;

    public ValidationErrorResponse(int status, String error, String message, String path, Map<String, String> fieldErrors) {
        super(status, error, message, path);
        this.fieldErrors = fieldErrors;
    }
}
