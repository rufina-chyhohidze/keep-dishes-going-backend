package be.kdg.backend.restaurant.adapter.in.listeners.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * listens to:
 * delivery.*.order.pickedup.v1
 * delivery.*.order.delivered.v1
 * delivery.*.order.location.v1
 *
 * Subscribe to all relevant delivery events
 * Log incoming messages
 * Verify end-to-end messaging works
 */
@Component
public class DeliveryEventListener {
    private static final Logger log = LoggerFactory.getLogger(DeliveryEventListener.class);

    @RabbitListener(bindings = @org.springframework.amqp.rabbit.annotation.QueueBinding(
            value = @org.springframework.amqp.rabbit.annotation.Queue,
            exchange = @org.springframework.amqp.rabbit.annotation.Exchange(name = "kdg.events", type = "topic"),
            key = {
                    "delivery.*.order.pickedup.v1",
                    "delivery.*.order.delivered.v1",
                    "delivery.*.order.location.v1"
            }
    ))
    public void handleDeliveryEvents(String message) {
        log.info("Received delivery event: {}", message);
        // TODO: deserialize JSON, update projection or state
    }
}
