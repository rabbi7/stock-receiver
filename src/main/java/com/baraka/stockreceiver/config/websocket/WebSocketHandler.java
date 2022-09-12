package com.baraka.stockreceiver.config.websocket;

import com.baraka.stockreceiver.dto.websocket.DataDto;
import com.baraka.stockreceiver.dto.websocket.WebSocketMarketDataDto;
import com.baraka.stockreceiver.service.StockParcerInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.math.BigDecimal;
import java.util.Calendar;

@ClientEndpoint
@Slf4j
public class WebSocketHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final StockParcerInterface parcer;

    public WebSocketHandler(StockParcerInterface parcer) {
        this.parcer = parcer;
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info("Connected to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void processMessage(String message) {
        WebSocketMarketDataDto webSocketData = null;
        try {
            webSocketData = mapper.readValue(message, WebSocketMarketDataDto.class);
            DataDto data = webSocketData.getData().get(0);

            Calendar priceDate = Calendar.getInstance();
            priceDate.setTimeInMillis(data.getT());

            parcer.newPriceReceived(data.getS(), data.getP(), priceDate);
        } catch (JsonProcessingException e) {
            log.error("Unprocessable market data {}", message);
        }
    }

    @OnError
    public void processError(Throwable t) {
        log.error("WebSocket processing error {}", t);
    }
}
