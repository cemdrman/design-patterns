package designpatterns.behavioral.observer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Concrete observer: reacts only when a price exceeds a threshold. */
public class PriceAlert implements StockObserver {

    private final String symbol;
    private final BigDecimal threshold;
    private final List<String> alerts = new ArrayList<>();

    public PriceAlert(String symbol, BigDecimal threshold) {
        this.symbol = symbol;
        this.threshold = threshold;
    }

    @Override
    public void onPriceChanged(String symbol, BigDecimal oldPrice, BigDecimal newPrice) {
        if (this.symbol.equals(symbol) && newPrice.compareTo(threshold) > 0) {
            alerts.add("ALERT! " + symbol + " is above " + threshold + ": " + newPrice);
        }
    }

    public List<String> alerts() {
        return List.copyOf(alerts);
    }
}
