package designpatterns.behavioral.observer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Concrete observer: shows every change on the screen. */
public class MobileApp implements StockObserver {

    private final List<String> screen = new ArrayList<>();

    @Override
    public void onPriceChanged(String symbol, BigDecimal oldPrice, BigDecimal newPrice) {
        screen.add(symbol + ": " + (oldPrice == null ? "-" : oldPrice) + " -> " + newPrice);
    }

    public List<String> screen() {
        return List.copyOf(screen);
    }
}
