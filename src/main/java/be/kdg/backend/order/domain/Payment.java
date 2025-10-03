package be.kdg.backend.order.domain;

import java.util.UUID;

public class Payment {
    private final UUID paymentId;
    private final String provider;
    private PaymentStatus status;

    public Payment(UUID paymentId, String provider, PaymentStatus status) {
        this.paymentId = paymentId;
        this.provider = provider;
        this.status = status;
    }

    public UUID getPaymentId() { return paymentId; }
    public String getProvider() { return provider; }
    public PaymentStatus getStatus() { return status; }

    public void markAsCompleted() { this.status = PaymentStatus.COMPLETED; }
    public void markAsFailed() { this.status = PaymentStatus.FAILED; }
}
