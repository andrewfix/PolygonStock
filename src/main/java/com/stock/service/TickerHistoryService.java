package com.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.client.PolygonClient;
import com.stock.dto.TickerRequestDTO;
import com.stock.dto.TickerResponseDTO;
import com.stock.dto.TickerResponseDetailDTO;
import com.stock.entity.Ticker;
import com.stock.entity.TickerHistory;
import com.stock.entity.User;
import com.stock.exception.PolygonClientException;
import com.stock.repository.TickerHistoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TickerHistoryService {
    private final TickerHistoryRepository tickerHistoryRepository;
    private final PolygonClient polygonClient;
    private final ObjectMapper objectMapper;

    @Value("${spring.app.PolygonClient.apiKey}")
    private String apiKey;

    @Transactional
    public void addTickerHistory(User user, TickerRequestDTO tickerRequestDTO) throws JsonProcessingException {

        Map<String, Object> tickerInfo = polygonClient.getTickerInfo(tickerRequestDTO.getTicker(), LocalDate.parse(tickerRequestDTO.getStart()), LocalDate.parse(tickerRequestDTO.getEnd()), "Bearer " + apiKey);
        String tickerInfoJson = objectMapper.writeValueAsString(tickerInfo);
        tickerHistoryRepository.addTickerHistory(user.getId(), tickerInfoJson);
    }

    public TickerResponseDTO getTickerHistory(User user, String ticker) {
        List<TickerHistory> histories = tickerHistoryRepository.findByUserAndTicker(user.getId(), ticker);

        List<TickerResponseDetailDTO> details = histories.stream()
                .map(history -> new TickerResponseDetailDTO(
                        history.getTradingDay(),
                        history.getPriceOpen(),
                        history.getPriceClose(),
                        history.getPriceLow(),
                        history.getPriceHigh()
                )).toList();

        return new TickerResponseDTO(
                user.getId(),
                ticker,
                details
        );
    }
}
