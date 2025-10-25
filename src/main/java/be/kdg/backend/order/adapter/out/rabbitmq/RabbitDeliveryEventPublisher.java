package be.kdg.backend.order.adapter.out.rabbitmq;

import be.kdg.backend.order.port.out.PublishDeliveryEventPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * made on agreed routing key pattern
 * restaurant.<restaurantId>.order.accepted.v1
 * restaurant.<restaurantId>.order.ready.v1
 */
@Component
public class RabbitDeliveryEventPublisher implements PublishDeliveryEventPort {
    private static final Logger log = LoggerFactory.getLogger(RabbitDeliveryEventPublisher.class);
    private static final String EXCHANGE = "kdg.events";

    private final RabbitTemplate rabbitTemplate;

    public RabbitDeliveryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishOrderAccepted(UUID restaurantId, UUID orderId) {
        String routingKey = String.format("restaurant.%s.order.accepted.v1", restaurantId);
        Map<String, Object> payload = new HashMap<>();
        payload.put("eventId", UUID.randomUUID().toString());
        payload.put("occurredAt", Instant.now().toString());
        payload.put("restaurantId", restaurantId.toString());
        payload.put("orderId", orderId.toString());

        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, payload);
        log.info("Published OrderAccepted event to {} with payload {}", routingKey, payload);
    }

    @Override
    public void publishOrderReady(UUID restaurantId, UUID orderId) {
        String routingKey = String.format("restaurant.%s.order.ready.v1", restaurantId);
        Map<String, Object> payload = new HashMap<>();
        payload.put("eventId", UUID.randomUUID().toString());
        payload.put("occurredAt", Instant.now().toString());
        payload.put("restaurantId", restaurantId.toString());
        payload.put("orderId", orderId.toString());
        //pickup/dropoff addresses here

        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, payload);
        log.info(" Published OrderReady event to {} with payload {}", routingKey, payload);
    }
}
