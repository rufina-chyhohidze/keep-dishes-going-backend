package be.kdg.backend.order.port.out;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentPort {
    /**
     * Creates a Stripe checkout session and returns a URL to redirect the customer to.
     */
    String createPaymentLink(UUID orderId, BigDecimal amount);
}
