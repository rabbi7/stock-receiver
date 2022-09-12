package com.baraka.stockreceiver.dto.websocket;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;



@AllArgsConstructor
@Getter
@NoArgsConstructor
public class DataDto {
    private BigDecimal p;
    private String s;
    private Long t;
    private int v;
}
