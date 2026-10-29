package designpatterns.behavioral.state;

/** Concrete states. Stateless, so a single shared instance of each is enough. */
public enum OrderStates implements OrderState {

    NEW {
        @Override
        public OrderState pay(Order order) {
            order.log("payment received");
            return PAID;
        }

        @Override
        public OrderState cancel(Order order) {
            order.log("cancelled before payment");
            return CANCELLED;
        }
    },
    PAID {
        @Override
        public OrderState ship(Order order) {
            order.log("handed over to cargo");
            return SHIPPED;
        }

        @Override
        public OrderState cancel(Order order) {
            order.log("cancelled, money refunded");
            return CANCELLED;
        }
    },
    SHIPPED {
        @Override
        public OrderState deliver(Order order) {
            order.log("delivered to customer");
            return DELIVERED;
        }
    },
    DELIVERED,
    CANCELLED
}
