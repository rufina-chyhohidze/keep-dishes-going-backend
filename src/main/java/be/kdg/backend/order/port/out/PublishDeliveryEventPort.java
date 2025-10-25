package be.kdg.backend.order.port.out;

import java.util.UUID;

public interface PublishDeliveryEventPort {
    void publishOrderAccepted(UUID restaurantId, UUID orderId);
    void publishOrderReady(UUID restaurantId, UUID orderId);
}
