package com.baraka.stockreceiver.model.market;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Builder
public class Candle {

    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    private Date startDateTime;

    public Candle(Date startDateTime, BigDecimal price) {
        open = price;
        close = price;
        high = price;
        low = price;
        this.startDateTime = startDateTime;
    }

    public void recalculateCandle(BigDecimal price) {
        if(price.compareTo(high) == 1) {
            high = price;
        }
        if(price.compareTo(low) == -1) {
            low = price;
        }
        close = price;
    }


}
