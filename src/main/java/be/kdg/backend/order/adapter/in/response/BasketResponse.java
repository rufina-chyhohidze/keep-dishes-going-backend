package be.kdg.backend.order.adapter.in.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BasketResponse(
        UUID basketId,
        UUID restaurantId,
        String status,
        BigDecimal totalPrice,
        List<ItemResponse> items
) {
    public record ItemResponse(
            UUID dishId,
            String dishName,
            BigDecimal unitPrice,
            int quantity
    ) {}
}

