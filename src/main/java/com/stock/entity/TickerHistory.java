package com.stock.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "ticker_history", schema = "stock")
@Data
@NoArgsConstructor
public class TickerHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ticker_id", nullable = false)
    private Ticker ticker;

    @NotNull
    @Column(name = "trading_day", nullable = false)
    private LocalDate tradingDay;

    @Column(name = "price_open", precision = 10, scale = 2)
    private BigDecimal priceOpen;

    @Column(name = "price_close", precision = 10, scale = 2)
    private BigDecimal priceClose;

    @Column(name = "price_low", precision = 10, scale = 2)
    private BigDecimal priceLow;

    @Column(name = "price_high", precision = 10, scale = 2)
    private BigDecimal priceHigh;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}