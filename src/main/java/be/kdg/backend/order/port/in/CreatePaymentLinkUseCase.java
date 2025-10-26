package be.kdg.backend.order.port.in;

import java.util.UUID;

public interface CreatePaymentLinkUseCase {
    String createPaymentLink(UUID orderId);
}
