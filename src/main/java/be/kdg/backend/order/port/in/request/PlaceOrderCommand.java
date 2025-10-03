package be.kdg.backend.order.port.in.request;

import be.kdg.backend.order.domain.CustomerInfo;
import be.kdg.backend.order.domain.OrderLine;
import be.kdg.backend.order.domain.Payment;

import java.util.List;
import java.util.UUID;

public record PlaceOrderCommand(UUID restaurantId,
                                CustomerInfo customerInfo,
                                List<OrderLine> orderLines,
                                Payment payment) {
}
