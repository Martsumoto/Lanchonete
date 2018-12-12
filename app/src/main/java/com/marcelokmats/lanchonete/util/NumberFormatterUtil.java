package com.marcelokmats.lanchonete.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

public class NumberFormatterUtil {


    public static String getCurrencyString(Double value) {
        return NumberFormatterUtil.getCurrencyString(value == null ? BigDecimal.ZERO : BigDecimal.valueOf(value));
    }

    public static String getCurrencyString(BigDecimal value) {
        Currency currency = Currency.getInstance("brl");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);

        return format.format(value == null ? 0 : value);
    }
}
