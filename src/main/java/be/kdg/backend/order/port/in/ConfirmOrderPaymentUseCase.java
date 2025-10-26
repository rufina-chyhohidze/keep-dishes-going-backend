package be.kdg.backend.order.port.in;

import java.util.UUID;

public interface ConfirmOrderPaymentUseCase {
    void confirmPayment(UUID orderId);
}
