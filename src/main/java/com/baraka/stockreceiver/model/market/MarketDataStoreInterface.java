package com.baraka.stockreceiver.model.market;

import com.baraka.stockreceiver.dto.CandleResponseDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MarketDataStoreInterface {

    void writeCurrentCandles();
    void updateCurrentCandles(String symbol, BigDecimal price, Date date);
    List<Candle> getCandles(String symbol);
}
