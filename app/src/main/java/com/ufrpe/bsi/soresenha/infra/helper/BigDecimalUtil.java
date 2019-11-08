package com.ufrpe.bsi.soresenha.infra.helper;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static BigDecimal fromBRLString(String s) {
        String cleanString = s.replaceAll("[R$,.]", "");
        return new BigDecimal(cleanString)
                .setScale(2, BigDecimal.ROUND_FLOOR)
                .divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
    }
}
