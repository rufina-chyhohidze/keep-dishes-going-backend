package be.kdg.backend.order.adapter.in;

import be.kdg.backend.order.adapter.in.dto.PaymentRequest;
import be.kdg.backend.order.adapter.in.dto.PlaceOrderRequest;
import be.kdg.backend.order.adapter.in.dto.RejectOrderRequest;
import be.kdg.backend.order.domain.CustomerInfo;
import be.kdg.backend.order.domain.OrderLine;
import be.kdg.backend.order.domain.Payment;
import be.kdg.backend.order.domain.PaymentStatus;
import be.kdg.backend.order.port.in.AcceptOrderUseCase;
import be.kdg.backend.order.port.in.MarkOrderReadyUseCase;
import be.kdg.backend.order.port.in.PlaceOrderUseCase;
import be.kdg.backend.order.port.in.RejectOrderUseCase;
import be.kdg.backend.order.port.in.request.PlaceOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final PlaceOrderUseCase placeOrderUseCase;
    private final AcceptOrderUseCase acceptOrderUseCase;
    private final RejectOrderUseCase rejectOrderUseCase;
    private final MarkOrderReadyUseCase markOrderReadyUseCase;

    public OrderController(PlaceOrderUseCase placeOrderUseCase,AcceptOrderUseCase acceptOrderUseCase,RejectOrderUseCase rejectOrderUseCase,MarkOrderReadyUseCase markOrderReadyUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
        this.acceptOrderUseCase = acceptOrderUseCase;
        this.rejectOrderUseCase = rejectOrderUseCase;
        this.markOrderReadyUseCase = markOrderReadyUseCase;
    }

    @PostMapping
    public ResponseEntity<UUID> placeOrder(@RequestBody PlaceOrderRequest request) {

        CustomerInfo customerInfo = new CustomerInfo(
                request.name(),
                request.email(),
                request.street(),
                request.number(),
                request.postalCode(),
                request.city(),
                request.country()
        );

        List<OrderLine> orderLines = request.orderLines().stream()
                .map(l -> new OrderLine(
                        l.dishId(),
                        l.quantity(),
                        l.priceAtCheckout()
                ))
                .toList();

        PaymentRequest pr = request.payment();
        Payment payment = new Payment(
                pr.paymentId(),
                pr.provider(),
                PaymentStatus.valueOf(pr.status())
        );

        PlaceOrderCommand command = new PlaceOrderCommand(
                request.restaurantId(),
                customerInfo,
                orderLines,
                payment
        );

        UUID orderId = placeOrderUseCase.placeOrder(command);
        log.info("Order placed with ID {}", orderId);

        return ResponseEntity.ok(orderId);
    }
    @PostMapping("/{orderId}/accept")
    public ResponseEntity<Void> acceptOrder(@PathVariable UUID orderId) {
        acceptOrderUseCase.acceptOrder(orderId);
        log.info("Order {} accepted", orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/reject")
    public ResponseEntity<Void> rejectOrder(@PathVariable UUID orderId, @RequestBody RejectOrderRequest request) {
        rejectOrderUseCase.rejectOrder(orderId, request.reason());
        log.info("Order {} rejected with reason: {}", orderId, request.reason());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/ready")
    public ResponseEntity<Void> markReady(@PathVariable UUID orderId) {
        markOrderReadyUseCase.markReady(orderId);
        log.info("Order {} marked as ready", orderId);
        return ResponseEntity.ok().build();
    }
}
