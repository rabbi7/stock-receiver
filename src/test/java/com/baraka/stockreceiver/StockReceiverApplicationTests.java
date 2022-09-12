package com.baraka.stockreceiver;

import com.baraka.stockreceiver.controller.StockReceiverController;
import com.baraka.stockreceiver.dto.CandleResponseDto;
import com.baraka.stockreceiver.mapper.CandleMapperImpl;
import com.baraka.stockreceiver.service.StockParcerService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.websocket.WebSocketContainer;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StockReceiverApplicationTests {

	private static final String SYMBOL_NAME_1 = "OZON";
	private static final String SYMBOL_NAME_2 = "KPN";
	private static final String SYMBOL_NAME_3 = "RND";

	@LocalServerPort
	private int port;

	@Autowired
	CandleMapperImpl candleMapper;

	@Autowired
	StockReceiverController stockReceiverController;

	@MockBean
	WebSocketContainer webSocketContainer;

	@Autowired
	StockParcerService stockParcerService;

	@Autowired
	private TestRestTemplate restTemplate;


	@Before
	public void setup() {
	}

	@Test
	void contextLoads() {
		assertThat(stockReceiverController).isNotNull();
		int j = 0;
	}

	@Test
	void testSingleSymbol() {

		Calendar date = Calendar.getInstance();
		//first minute
		date.set(2022, 9, 11, 17, 12, 23);

		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(10), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(12), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(13), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(14), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(13), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(12), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(6), date);

		//secondMinute
		date.set(2022, 9, 11, 17, 13, 23);

		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(20), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(22), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(23), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(24), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(23), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(22), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(166), date);

		//third minute
		date.set(2022, 9, 11, 17, 14, 23);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(30), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(33), date);

		ResponseEntity<List<CandleResponseDto>> responseEntity = restTemplate.exchange(
				"/symbols/" + SYMBOL_NAME_1 + "/candles", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CandleResponseDto>>() {});


		List<CandleResponseDto> resultCandles = responseEntity.getBody();
		assertThat(resultCandles.size()).isEqualTo(3);
		assertThat(resultCandles.get(0).getClose()).isEqualTo(BigDecimal.valueOf(6));
		assertThat(resultCandles.get(0).getOpen()).isEqualTo(BigDecimal.valueOf(10));
		assertThat(resultCandles.get(0).getHigh()).isEqualTo(BigDecimal.valueOf(14));
		assertThat(resultCandles.get(0).getLow()).isEqualTo(BigDecimal.valueOf(6));

		assertThat(resultCandles.get(1).getClose()).isEqualTo(BigDecimal.valueOf(166));
		assertThat(resultCandles.get(1).getOpen()).isEqualTo(BigDecimal.valueOf(20));
		assertThat(resultCandles.get(1).getHigh()).isEqualTo(BigDecimal.valueOf(166));
		assertThat(resultCandles.get(1).getLow()).isEqualTo(BigDecimal.valueOf(20));

		assertThat(resultCandles.get(2).getClose()).isEqualTo(BigDecimal.valueOf(33));
		assertThat(resultCandles.get(2).getOpen()).isEqualTo(BigDecimal.valueOf(30));
		assertThat(resultCandles.get(2).getHigh()).isEqualTo(BigDecimal.valueOf(33));
		assertThat(resultCandles.get(2).getLow()).isEqualTo(BigDecimal.valueOf(30));

	}


	@Test
	void testThreeSymbols() {

		Calendar date = Calendar.getInstance();
		//first minute
		date.set(2022, 9, 11, 17, 12, 23);

		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(2), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(3), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(2), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(1), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(5), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(6), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(7), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(8), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(100), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(100), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(100), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(100), date);

		//secondMinute
		date.set(2022, 9, 11, 17, 13, 23);

		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(5), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(6), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(5), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(4), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(3), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(4), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(1), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(6), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(200), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(200), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(200), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(200), date);

		//third minute
		date.set(2022, 9, 11, 17, 14, 23);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(11), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(12), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(13), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_1, BigDecimal.valueOf(14), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(13), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(14), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(11), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_2, BigDecimal.valueOf(16), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(300), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(300), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(300), date);
		stockParcerService.newPriceReceived(SYMBOL_NAME_3, BigDecimal.valueOf(300), date);

		ResponseEntity<List<CandleResponseDto>> responseEntity = restTemplate.exchange(
				"/symbols/" + SYMBOL_NAME_1 + "/candles", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CandleResponseDto>>() {});

		List<CandleResponseDto> resultCandles = responseEntity.getBody();
		assertThat(resultCandles.size()).isEqualTo(3);
		assertThat(resultCandles.get(0).getClose()).isEqualTo(BigDecimal.valueOf(1));
		assertThat(resultCandles.get(0).getOpen()).isEqualTo(BigDecimal.valueOf(2));
		assertThat(resultCandles.get(0).getHigh()).isEqualTo(BigDecimal.valueOf(3));
		assertThat(resultCandles.get(0).getLow()).isEqualTo(BigDecimal.valueOf(1));

		assertThat(resultCandles.get(1).getClose()).isEqualTo(BigDecimal.valueOf(4));
		assertThat(resultCandles.get(1).getOpen()).isEqualTo(BigDecimal.valueOf(5));
		assertThat(resultCandles.get(1).getHigh()).isEqualTo(BigDecimal.valueOf(6));
		assertThat(resultCandles.get(1).getLow()).isEqualTo(BigDecimal.valueOf(4));

		assertThat(resultCandles.get(2).getClose()).isEqualTo(BigDecimal.valueOf(14));
		assertThat(resultCandles.get(2).getOpen()).isEqualTo(BigDecimal.valueOf(11));
		assertThat(resultCandles.get(2).getHigh()).isEqualTo(BigDecimal.valueOf(14));
		assertThat(resultCandles.get(2).getLow()).isEqualTo(BigDecimal.valueOf(11));

		ResponseEntity<List<CandleResponseDto>> responseEntity2 = restTemplate.exchange(
				"/symbols/" + SYMBOL_NAME_2 + "/candles", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CandleResponseDto>>() {});

		List<CandleResponseDto> resultCandles2 = responseEntity2.getBody();
		assertThat(resultCandles2.size()).isEqualTo(3);
		assertThat(resultCandles2.get(0).getClose()).isEqualTo(BigDecimal.valueOf(8));
		assertThat(resultCandles2.get(0).getOpen()).isEqualTo(BigDecimal.valueOf(5));
		assertThat(resultCandles2.get(0).getHigh()).isEqualTo(BigDecimal.valueOf(8));
		assertThat(resultCandles2.get(0).getLow()).isEqualTo(BigDecimal.valueOf(5));

		assertThat(resultCandles2.get(1).getClose()).isEqualTo(BigDecimal.valueOf(6));
		assertThat(resultCandles2.get(1).getOpen()).isEqualTo(BigDecimal.valueOf(3));
		assertThat(resultCandles2.get(1).getHigh()).isEqualTo(BigDecimal.valueOf(6));
		assertThat(resultCandles2.get(1).getLow()).isEqualTo(BigDecimal.valueOf(1));

		assertThat(resultCandles2.get(2).getClose()).isEqualTo(BigDecimal.valueOf(16));
		assertThat(resultCandles2.get(2).getOpen()).isEqualTo(BigDecimal.valueOf(13));
		assertThat(resultCandles2.get(2).getHigh()).isEqualTo(BigDecimal.valueOf(16));
		assertThat(resultCandles2.get(2).getLow()).isEqualTo(BigDecimal.valueOf(11));

		ResponseEntity<List<CandleResponseDto>> responseEntity3 = restTemplate.exchange(
				"/symbols/" + SYMBOL_NAME_3 + "/candles", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<CandleResponseDto>>() {});


		List<CandleResponseDto> resultCandles3 = responseEntity3.getBody();
		assertThat(resultCandles3.size()).isEqualTo(3);
		assertThat(resultCandles3.get(0).getClose()).isEqualTo(BigDecimal.valueOf(100));
		assertThat(resultCandles3.get(0).getOpen()).isEqualTo(BigDecimal.valueOf(100));
		assertThat(resultCandles3.get(0).getHigh()).isEqualTo(BigDecimal.valueOf(100));
		assertThat(resultCandles3.get(0).getLow()).isEqualTo(BigDecimal.valueOf(100));

		assertThat(resultCandles3.get(1).getClose()).isEqualTo(BigDecimal.valueOf(200));
		assertThat(resultCandles3.get(1).getOpen()).isEqualTo(BigDecimal.valueOf(200));
		assertThat(resultCandles3.get(1).getHigh()).isEqualTo(BigDecimal.valueOf(200));
		assertThat(resultCandles3.get(1).getLow()).isEqualTo(BigDecimal.valueOf(200));

		assertThat(resultCandles3.get(2).getClose()).isEqualTo(BigDecimal.valueOf(300));
		assertThat(resultCandles3.get(2).getOpen()).isEqualTo(BigDecimal.valueOf(300));
		assertThat(resultCandles3.get(2).getHigh()).isEqualTo(BigDecimal.valueOf(300));
		assertThat(resultCandles3.get(2).getLow()).isEqualTo(BigDecimal.valueOf(300));

	}


}
