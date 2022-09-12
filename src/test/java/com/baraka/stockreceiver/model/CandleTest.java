package com.baraka.stockreceiver.model;

import com.baraka.stockreceiver.model.market.Candle;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CandleTest {

    @Test
    public void testSettingNewHighInCandle() {
        Candle candle = Candle.builder()
                .open(BigDecimal.valueOf(32))
                .close(BigDecimal.valueOf(1))
                .high(BigDecimal.valueOf(15))
                .low(BigDecimal.valueOf(0.5))
                .build();

        candle.recalculateCandle(BigDecimal.valueOf(33));

        assertThat(candle.getHigh().compareTo(BigDecimal.valueOf(33))).isEqualTo(0);
        assertThat(candle.getLow().compareTo(BigDecimal.valueOf(0.5))).isEqualTo(0);
        assertThat(candle.getOpen().compareTo(BigDecimal.valueOf(32))).isEqualTo(0);
        assertThat(candle.getClose().compareTo(BigDecimal.valueOf(33))).isEqualTo(0);
    }

    @Test
    public void testSettingNewLowInCandle() {
        Candle candle = Candle.builder()
                .open(BigDecimal.valueOf(15))
                .close(BigDecimal.valueOf(1))
                .high(BigDecimal.valueOf(32))
                .low(BigDecimal.valueOf(0.5))
                .build();

        candle.recalculateCandle(BigDecimal.valueOf(0.1));

        assertThat(candle.getHigh().compareTo(BigDecimal.valueOf(32))).isEqualTo(0);
        assertThat(candle.getLow().compareTo(BigDecimal.valueOf(0.1))).isEqualTo(0);
        assertThat(candle.getOpen().compareTo(BigDecimal.valueOf(15))).isEqualTo(0);
        assertThat(candle.getClose().compareTo(BigDecimal.valueOf(0.1))).isEqualTo(0);
    }

    @Test
    public void settingNewPriceInBetween() {
        Candle candle = Candle.builder()
                .open(BigDecimal.valueOf(15))
                .close(BigDecimal.valueOf(1))
                .high(BigDecimal.valueOf(32))
                .low(BigDecimal.valueOf(0.5))
                .build();

        candle.recalculateCandle(BigDecimal.valueOf(7));

        assertThat(candle.getHigh().compareTo(BigDecimal.valueOf(32))).isEqualTo(0);
        assertThat(candle.getLow().compareTo(BigDecimal.valueOf(0.5))).isEqualTo(0);
        assertThat(candle.getOpen().compareTo(BigDecimal.valueOf(15))).isEqualTo(0);
        assertThat(candle.getClose().compareTo(BigDecimal.valueOf(7))).isEqualTo(0);
    }
}
