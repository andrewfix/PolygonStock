package com.stock.dto;

import com.stock.annotation.ValidDateFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@NoArgsConstructor
public class TickerRequestDTO {
    @NotBlank(message = "Empty ticker")
    private String ticker;
    @NotNull(message = "Empty start date")
    @ValidDateFormat(message = "Start date must be in the format yyyy-MM-dd")
    private String start;
    @NotNull(message = "Empty end date")
    @ValidDateFormat(message = "End date must be in the format yyyy-MM-dd")
    private String end;

    @AssertTrue(message = "Start date must be before end date")
    public boolean isValidDateRange() {
        if (start == null || end == null) {
            return true;
        }

        try {
            // Парсим строки в LocalDate
            LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return startDate.isBefore(endDate); // Проверяем, что start < end
        } catch (DateTimeParseException e) {
            return true;
        }
    }
}
