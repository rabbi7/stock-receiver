package com.baraka.stockreceiver.config;

import com.baraka.stockreceiver.config.websocket.WebSocketHandler;
import com.baraka.stockreceiver.model.market.HashMapMarketData;
import com.baraka.stockreceiver.model.market.MarketDataStoreInterface;
import com.baraka.stockreceiver.service.StockParcerService;
import com.baraka.stockreceiver.service.StockParcerInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Value( "${socket.url}" )
    private String socketUri;

    @Bean
    public MarketDataStoreInterface marketData() {
        return HashMapMarketData.builder()
                .marketData(new HashMap<>())
                .currentCandles(new HashMap<>())
                .build();
    }

    @Bean
    public StockParcerInterface webSocketStockParcer(MarketDataStoreInterface hashMapMarketData) {
        return new StockParcerService(hashMapMarketData);
    }

    @Bean
    public WebSocketContainer webSocketContainer(StockParcerInterface webSocketStockParcer) throws IOException, DeploymentException, InterruptedException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        //TODO: set URI to external parameters
        log.info("Connecting to " + socketUri);
        container.connectToServer(new WebSocketHandler(webSocketStockParcer), URI.create(socketUri));

        return container;
    }
}
