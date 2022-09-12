package com.baraka.stockreceiver.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class WebSocketMarketDataDto {

    private List<DataDto> data;
    private TypeDto type;
}
