package be.kdg.backend.order.adapter.in.dto;

import java.util.UUID;

public record PaymentRequest (
        UUID paymentId, String provider, String status
){
}
