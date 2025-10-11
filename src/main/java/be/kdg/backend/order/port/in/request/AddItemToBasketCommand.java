package be.kdg.backend.order.port.in.request;

import java.math.BigDecimal;
import java.util.UUID;

public record AddItemToBasketCommand(   UUID basketId,
                                        UUID restaurantId,
                                        UUID dishId,
                                        String dishName,
                                        double price,
                                        int quantity) {
}
