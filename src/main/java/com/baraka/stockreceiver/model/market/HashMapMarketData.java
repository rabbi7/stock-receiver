package com.baraka.stockreceiver.model.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;

@Builder
@AllArgsConstructor
@Getter
public class HashMapMarketData implements MarketDataStoreInterface {

    private HashMap<String, List<Candle>> marketData;
    private HashMap<String, Candle> currentCandles;

    @Override
    public void writeCurrentCandles() {
        for(String key: currentCandles.keySet()) {
            Candle current = currentCandles.get(key);
            if(!marketData.containsKey(key)) {
                marketData.put(key, new LinkedList<>(Arrays.asList(current)));
            } else {
                List<Candle> symbolCandles = marketData.get(key);
                symbolCandles.add(current);
                marketData.put(key, symbolCandles);
            }
        }
        currentCandles = new HashMap<>();
    }

    @Override
    public void updateCurrentCandles(String symbol, BigDecimal price, Date dateTime) {
        if(!currentCandles.containsKey(symbol))
            currentCandles.put(symbol, new Candle(dateTime, price));
        else {
            Candle candle = currentCandles.get(symbol);
            candle.recalculateCandle(price);
            currentCandles.put(symbol, candle);
        }
    }

    @Override
    public List<Candle> getCandles(String symbol) {
        ArrayList<Candle> candles = new ArrayList<>();

        if(marketData.containsKey(symbol)) {
            candles.addAll(marketData.get(symbol));
        }
        if(currentCandles.containsKey(symbol)) {
            candles.add(currentCandles.get(symbol));
        }


        return candles;
    }
}
