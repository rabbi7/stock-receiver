package com.baraka.stockreceiver.mapper;

import com.baraka.stockreceiver.dto.CandleResponseDto;
import com.baraka.stockreceiver.model.market.Candle;
import org.mapstruct.Mapper;

@Mapper
public interface CandleMapper {

    CandleResponseDto toCandleResponseDto(Candle candle);
}


