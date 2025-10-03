package be.kdg.backend.order.adapter.in.dto;

import java.util.List;
import java.util.UUID;

public record PlaceOrderRequest(
        UUID restaurantId,
        String name,
        String email,
        String street,
        String number,
        String postalCode,
        String city,
        String country,
        List<OrderLineRequest> orderLines,
        PaymentRequest payment
) {
}
