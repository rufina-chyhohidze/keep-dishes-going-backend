package be.kdg.backend.order.port.in;

import be.kdg.backend.order.port.in.request.PlaceOrderCommand;

import java.util.UUID;

public interface PlaceOrderUseCase {
    UUID placeOrder(PlaceOrderCommand command);
}
