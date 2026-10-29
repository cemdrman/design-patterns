package designpatterns.behavioral;

import designpatterns.behavioral.observer.MobileApp;
import designpatterns.behavioral.observer.PriceAlert;
import designpatterns.behavioral.observer.StockMarket;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverTest {

    @Test
    void notifiesOnlySubscribedObserversOnRealChanges() {
        StockMarket market = new StockMarket();
        MobileApp app = new MobileApp();
        PriceAlert alert = new PriceAlert("X", new BigDecimal("10"));
        market.subscribe(app);
        market.subscribe(alert);

        market.updatePrice("X", new BigDecimal("9"));
        market.updatePrice("X", new BigDecimal("9.00")); // same value, no notification
        market.updatePrice("X", new BigDecimal("11"));
        market.unsubscribe(app);
        market.updatePrice("X", new BigDecimal("12"));

        assertEquals(2, app.screen().size());
        assertEquals(2, alert.alerts().size());
    }
}
