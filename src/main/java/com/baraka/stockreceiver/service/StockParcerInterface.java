package com.baraka.stockreceiver.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public interface StockParcerInterface {
    void newPriceReceived(String symbol, BigDecimal price, Calendar time);
}
