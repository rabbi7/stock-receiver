package com.baraka.stockreceiver.controller;

import com.baraka.stockreceiver.dto.CandleResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public interface StockReceiverControllerApi {

    @GetMapping(path = "symbols/{symbol}/candles",
            produces = APPLICATION_JSON_VALUE)
    List<CandleResponseDto> getCandles(@PathVariable String symbol);
}
