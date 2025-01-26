package com.stock.repository;

import com.stock.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerRepository extends JpaRepository<Ticker, String> {
}
