package designpatterns.behavioral.observer;

import java.math.BigDecimal;

/** Observer (subscriber) interface. */
@FunctionalInterface
public interface StockObserver {

    void onPriceChanged(String symbol, BigDecimal oldPrice, BigDecimal newPrice);
}
