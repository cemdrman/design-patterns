package designpatterns.refactoring.observer;

import designpatterns.behavioral.observer.MobileApp;
import designpatterns.behavioral.observer.PriceAlert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * BEFORE Observer – the subject calls every interested class directly.
 * <p>The stock market depends on the mobile app and the alert service. Adding an SMS notifier or a
 * trading bot means editing this class, and nobody can subscribe/unsubscribe at runtime.
 *
 * @see designpatterns.behavioral.observer.StockMarket the refactored version
 */
public class StockMarketBefore {

    private final Map<String, BigDecimal> prices = new HashMap<>();
    private final MobileApp mobileApp;
    private final PriceAlert priceAlert;

    public StockMarketBefore(MobileApp mobileApp, PriceAlert priceAlert) {
        this.mobileApp = mobileApp;
        this.priceAlert = priceAlert;
    }

    public void updatePrice(String symbol, BigDecimal newPrice) {
        BigDecimal oldPrice = prices.put(symbol, newPrice);
        if (oldPrice == null || oldPrice.compareTo(newPrice) != 0) {
            mobileApp.onPriceChanged(symbol, oldPrice, newPrice);
            priceAlert.onPriceChanged(symbol, oldPrice, newPrice);
            // smsService.send(...);   <- next sprint
            // tradingBot.react(...);  <- the sprint after
        }
    }
}
