package com.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TickerResponseDTO {
    private Integer id;
    private String ticker;
    private List<TickerResponseDetailDTO> data;
}
