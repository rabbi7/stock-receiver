package com.baraka.stockreceiver.service;

import com.baraka.stockreceiver.model.market.MarketDataStoreInterface;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class StockParcerService implements StockParcerInterface {

    private final MarketDataStoreInterface marketData;
    private static int currentMinute = 0;

    public StockParcerService(MarketDataStoreInterface marketData) {
        this.marketData = marketData;
    }

    @PostConstruct
    public void setInitialMinute() {
        Calendar currentCalendar = Calendar.getInstance();
        currentMinute = currentCalendar.get(Calendar.MINUTE);
    }

    @Override
    public void newPriceReceived(String symbol, BigDecimal price, Calendar time) {
        int candleMinute = time.get(Calendar.MINUTE);

        if(currentMinute != candleMinute) {
            log.info("Storing candles for {} minute ", currentMinute);
            marketData.writeCurrentCandles();
            currentMinute = candleMinute;
        }

        marketData.updateCurrentCandles(symbol, price, time.getTime());
    }

}
