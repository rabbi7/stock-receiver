package com.baraka.stockreceiver.mapper;

import com.baraka.stockreceiver.model.market.Candle;
import com.baraka.stockreceiver.model.market.HashMapMarketData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HashMapMarketDataTest {

    public static final String OZON = "OZON";
    public static final String TDG = "TDG";
    public static final String RBC = "RBC";

    @Test
    public void testGettingCandlesWhenThereIsNoCurrentOnes() {
        HashMap<String, List<Candle>> marketData = new HashMap<>();

        Candle ozonCandle = Candle.builder().open(BigDecimal.valueOf(1))
                .close(BigDecimal.valueOf(15))
                .high(BigDecimal.valueOf(32))
                .low(BigDecimal.valueOf(0.1))
                .build();
        marketData.put(OZON, Arrays.asList(ozonCandle));

        HashMapMarketData mapMarketData = HashMapMarketData.builder()
                .marketData(marketData)
                .currentCandles(new HashMap<>())
                .build();

        assertThat(mapMarketData.getCurrentCandles().size()).isEqualTo(0);

        List<Candle> resultOzonCandles = mapMarketData.getMarketData().get(OZON);
        assertThat(resultOzonCandles.size()).isEqualTo(1);
        assertThat(resultOzonCandles.get(0).getHigh().compareTo(BigDecimal.valueOf(32))).isEqualTo(0);
        assertThat(resultOzonCandles.get(0).getLow().compareTo(BigDecimal.valueOf(0.1))).isEqualTo(0);
        assertThat(resultOzonCandles.get(0).getOpen().compareTo(BigDecimal.valueOf(1))).isEqualTo(0);
        assertThat(resultOzonCandles.get(0).getClose().compareTo(BigDecimal.valueOf(15))).isEqualTo(0);
    }

    @Test
    public void testGettingCandlesWhenThereIsNoAlreadyBuiltOnes() {
        HashMap<String, Candle> currentCandleData = new HashMap<>();

        Candle ozonCandle = Candle.builder().open(BigDecimal.valueOf(1))
                .close(BigDecimal.valueOf(15))
                .high(BigDecimal.valueOf(32))
                .low(BigDecimal.valueOf(0.1))
                .build();
        currentCandleData.put(OZON, ozonCandle);

        HashMapMarketData mapMarketData = HashMapMarketData.builder()
                .marketData(new HashMap<>())
                .currentCandles(currentCandleData)
                .build();

        assertThat(mapMarketData.getMarketData().size()).isEqualTo(0);

        Candle resultOzonCandles = mapMarketData.getCurrentCandles().get(OZON);
        assertThat(resultOzonCandles.getHigh().compareTo(BigDecimal.valueOf(32))).isEqualTo(0);
        assertThat(resultOzonCandles.getLow().compareTo(BigDecimal.valueOf(0.1))).isEqualTo(0);
        assertThat(resultOzonCandles.getOpen().compareTo(BigDecimal.valueOf(1))).isEqualTo(0);
        assertThat(resultOzonCandles.getClose().compareTo(BigDecimal.valueOf(15))).isEqualTo(0);
    }

    @Test
    public void testCandlesStoredCorrectly() {
        HashMapMarketData mapMarketData = HashMapMarketData.builder()
                .marketData(new HashMap<>())
                .currentCandles(new HashMap<>())
                .build();

        Calendar startTime = Calendar.getInstance();
        startTime.setTime(Date.from(Instant.now()));

        mapMarketData.updateCurrentCandles(OZON, BigDecimal.valueOf(1), startTime.getTime());
        mapMarketData.updateCurrentCandles(OZON, BigDecimal.valueOf(0.1), startTime.getTime());
        mapMarketData.updateCurrentCandles(OZON, BigDecimal.valueOf(32), startTime.getTime());
        mapMarketData.updateCurrentCandles(OZON, BigDecimal.valueOf(15), startTime.getTime());
        mapMarketData.updateCurrentCandles(TDG, BigDecimal.valueOf(1), startTime.getTime());
        mapMarketData.updateCurrentCandles(TDG, BigDecimal.valueOf(1), startTime.getTime());
        mapMarketData.updateCurrentCandles(TDG, BigDecimal.valueOf(1), startTime.getTime());
        mapMarketData.updateCurrentCandles(TDG, BigDecimal.valueOf(1), startTime.getTime());

        mapMarketData.writeCurrentCandles();

        mapMarketData.updateCurrentCandles(OZON, BigDecimal.valueOf(2), startTime.getTime());
        mapMarketData.updateCurrentCandles(OZON, BigDecimal.valueOf(3), startTime.getTime());
        mapMarketData.updateCurrentCandles(OZON, BigDecimal.valueOf(4), startTime.getTime());
        mapMarketData.updateCurrentCandles(OZON, BigDecimal.valueOf(5), startTime.getTime());
        mapMarketData.updateCurrentCandles(RBC, BigDecimal.valueOf(100), startTime.getTime());
        mapMarketData.updateCurrentCandles(RBC, BigDecimal.valueOf(120), startTime.getTime());
        mapMarketData.updateCurrentCandles(RBC, BigDecimal.valueOf(90), startTime.getTime());
        mapMarketData.updateCurrentCandles(RBC, BigDecimal.valueOf(50), startTime.getTime());

        assertThat(mapMarketData.getMarketData().size()).isEqualTo(2);
        assertThat(mapMarketData.getCurrentCandles().size()).isEqualTo(2);
        assertThat(mapMarketData.getCurrentCandles().containsKey(RBC)).isTrue();
        assertThat(mapMarketData.getCurrentCandles().containsKey(OZON)).isTrue();
        assertThat(mapMarketData.getMarketData().containsKey(OZON)).isTrue();
        assertThat(mapMarketData.getMarketData().containsKey(TDG)).isTrue();

        assertThat(mapMarketData.getCandles(OZON).size()).isEqualTo(2);
        assertThat(mapMarketData.getCandles(RBC).size()).isEqualTo(1);
        assertThat(mapMarketData.getCandles(TDG).size()).isEqualTo(1);
    }
}
