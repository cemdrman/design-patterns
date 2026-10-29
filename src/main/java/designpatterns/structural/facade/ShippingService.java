package designpatterns.structural.facade;

/** Subsystem class. */
public class ShippingService {

    private int sequence = 1000;

    public String createShipment(String customerId, String productId) {
        return "TRACK-" + (++sequence);
    }
}
