package com.stock.repository;

import com.stock.entity.TickerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TickerHistoryRepository extends JpaRepository<TickerHistory, Long> {
    @Procedure(procedureName = "add_ticker_history")
    void addTickerHistory(@Param("ticker_user_id") Integer userId, @Param("import_data") String tickerInfo);

    @Query("SELECT th FROM TickerHistory th " +
            "JOIN FETCH th.user u " +
            "JOIN FETCH th.ticker t " +
            "WHERE u.id = :userId AND t.tickerId = :tickerId")
    List<TickerHistory> findByUserAndTicker(@Param("userId") Integer userId, @Param("tickerId") String tickerId);
}