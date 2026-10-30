package designpatterns.spring.observer;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Observer #2: 1 point for every 100 TL. Added without touching {@link OrderService}. */
@Component
public class LoyaltyPointsListener {

    private final Map<String, Integer> points = new ConcurrentHashMap<>();

    @EventListener
    void on(OrderPlacedEvent event) {
        int earned = event.amount().divide(BigDecimal.valueOf(100), 0, RoundingMode.DOWN).intValue();
        points.merge(event.email(), earned, Integer::sum);
    }

    public int pointsOf(String email) {
        return points.getOrDefault(email, 0);
    }
}
