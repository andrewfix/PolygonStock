package com.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ticker", schema = "stock")
@Data
@NoArgsConstructor
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticker_id", nullable = false)
    private String tickerId;

    @OneToMany(mappedBy = "ticker")
    private List<TickerHistory> tickerHistories;
}