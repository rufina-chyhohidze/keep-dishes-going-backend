package be.kdg.backend.order.adapter.in.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderLineRequest(UUID dishId, int quantity, BigDecimal priceAtCheckout) {
}
