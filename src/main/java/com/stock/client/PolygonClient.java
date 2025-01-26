package com.stock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.util.Map;

@FeignClient(name = "PolygonClient", url = "https://api.polygon.io/v2/aggs")
public interface PolygonClient {
    @GetMapping("/ticker/{ticker}/range/1/day/{start}/{end}")
    Map<String, Object> getTickerInfo(@PathVariable("ticker") String ticker,
                                      @PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                      @PathVariable("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                      @RequestHeader("Authorization") String token);
}
