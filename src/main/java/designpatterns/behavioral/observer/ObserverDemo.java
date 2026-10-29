package designpatterns.behavioral.observer;

import designpatterns.common.Console;

import java.math.BigDecimal;

public class ObserverDemo {

    public static void main(String[] args) {
        Console.title("Observer");

        StockMarket market = new StockMarket();
        MobileApp app = new MobileApp();
        PriceAlert alert = new PriceAlert("THYAO", new BigDecimal("300"));
        StockObserver logger = (symbol, oldPrice, newPrice) -> Console.print("log", symbol + " changed");

        market.subscribe(app);
        market.subscribe(alert);
        market.subscribe(logger); // a lambda is also an observer

        market.updatePrice("THYAO", new BigDecimal("285.50"));
        market.updatePrice("ASELS", new BigDecimal("120.00"));
        market.updatePrice("THYAO", new BigDecimal("301.25"));

        market.unsubscribe(logger);
        market.updatePrice("ASELS", new BigDecimal("118.40"));

        Console.section("Observers");
        app.screen().forEach(line -> Console.print("mobile app", line));
        alert.alerts().forEach(line -> Console.print("price alert", line));
    }
}
