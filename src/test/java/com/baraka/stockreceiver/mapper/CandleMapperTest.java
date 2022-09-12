package com.baraka.stockreceiver.mapper;

import com.baraka.stockreceiver.dto.CandleResponseDto;
import com.baraka.stockreceiver.model.market.Candle;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CandleMapperTest {

    @Test
    public void testCandleMapper() {
        Candle candle = Candle.builder()
                .open(BigDecimal.valueOf(32))
                .close(BigDecimal.valueOf(1))
                .high(BigDecimal.valueOf(15))
                .low(BigDecimal.valueOf(0.5))
                .build();
        CandleMapperImpl candleMapper = new CandleMapperImpl();
        CandleResponseDto candleResponse = candleMapper.toCandleResponseDto(candle);

        assertThat(candleResponse.getClose().compareTo(candle.getClose())).isEqualTo(0);
        assertThat(candleResponse.getOpen().compareTo(candle.getOpen())).isEqualTo(0);
        assertThat(candleResponse.getHigh().compareTo(candle.getHigh())).isEqualTo(0);
        assertThat(candleResponse.getLow().compareTo(candle.getLow())).isEqualTo(0);
        assertThat(candleResponse.getStartDateTime()).isEqualTo(candle.getStartDateTime());

    }
}
