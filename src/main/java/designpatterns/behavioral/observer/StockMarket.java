package designpatterns.behavioral.observer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Subject (publisher): keeps a list of observers and notifies all of them
 * whenever its state changes. It does not know the concrete observer classes.
 */
public class StockMarket {

    // CopyOnWriteArrayList: observers may unsubscribe while being notified.
    private final List<StockObserver> observers = new CopyOnWriteArrayList<>();
    private final Map<String, BigDecimal> prices = new HashMap<>();

    public void subscribe(StockObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(StockObserver observer) {
        observers.remove(observer);
    }

    public void updatePrice(String symbol, BigDecimal newPrice) {
        BigDecimal oldPrice = prices.put(symbol, newPrice);
        if (oldPrice == null || oldPrice.compareTo(newPrice) != 0) {
            observers.forEach(observer -> observer.onPriceChanged(symbol, oldPrice, newPrice));
        }
    }
}
