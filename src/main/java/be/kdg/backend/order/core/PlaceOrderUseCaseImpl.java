package be.kdg.backend.order.core;

import be.kdg.backend.common.events.OrderPlacedEvent;
import be.kdg.backend.order.domain.Order;
import be.kdg.backend.order.port.in.PlaceOrderUseCase;
import be.kdg.backend.order.port.in.request.PlaceOrderCommand;
import be.kdg.backend.order.port.out.SaveOrderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlaceOrderUseCaseImpl implements PlaceOrderUseCase {

    private static final Logger log = LoggerFactory.getLogger(PlaceOrderUseCaseImpl.class);

    private final SaveOrderPort saveOrderPort;
    private final ApplicationEventPublisher publisher;

    public PlaceOrderUseCaseImpl(SaveOrderPort saveOrderPort, ApplicationEventPublisher publisher) {
        this.saveOrderPort = saveOrderPort;
        this.publisher = publisher;
    }

    @Override
    public UUID placeOrder(PlaceOrderCommand command) {
        UUID orderId = UUID.randomUUID();

        Order order = new Order(orderId,
                command.restaurantId(),
                command.customerInfo(),
                command.orderLines(),
                command.payment());

        saveOrderPort.save(order);

        double totalPrice = order.calculateTotalPrice();

        OrderPlacedEvent event = new OrderPlacedEvent(orderId,
                command.restaurantId(),
                UUID.randomUUID(),
                totalPrice);

        publisher.publishEvent(event);

        log.info("OrderPlacedEvent published: {}", event);
        return orderId;
    }

}
