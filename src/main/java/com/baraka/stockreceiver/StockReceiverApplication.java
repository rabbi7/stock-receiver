package com.baraka.stockreceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.WebSocket;

@SpringBootApplication
public class StockReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockReceiverApplication.class, args);
//		WebSocket ws = HttpClient
//				.newHttpClient()
//				.newWebSocketBuilder()
//				.buildAsync(URI.create("ws://b-mocks.dev.app.getbaraka.com:9989"), new WebSocketClient())
//				.join();
	}

}
