package com.baraka.stockreceiver.controller;

import com.baraka.stockreceiver.dto.CandleResponseDto;
import com.baraka.stockreceiver.mapper.CandleMapperImpl;
import com.baraka.stockreceiver.model.market.MarketDataStoreInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StockReceiverController implements StockReceiverControllerApi{

    private final MarketDataStoreInterface marketData;
    private final CandleMapperImpl candleMapper;

    @Override
    public List<CandleResponseDto> getCandles(String symbol) {
        return marketData.getCandles(symbol)
                .stream()
                .map(candleMapper::toCandleResponseDto)
                .collect(Collectors.toList());
    }
}
