package be.kdg.backend.order.adapter.in;

import be.kdg.backend.order.adapter.in.dto.PaymentRequest;
import be.kdg.backend.order.adapter.in.dto.PlaceOrderRequest;
import be.kdg.backend.order.adapter.in.dto.RejectOrderRequest;
import be.kdg.backend.order.domain.*;
import be.kdg.backend.order.port.in.*;
import be.kdg.backend.order.port.in.request.AcceptOrderCommand;
import be.kdg.backend.order.port.in.request.MarkOrderReadyCommand;
import be.kdg.backend.order.port.in.request.PlaceOrderCommand;
import be.kdg.backend.order.port.in.request.RejectOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private final CreatePaymentLinkUseCase createPaymentLinkUseCase;
    private final LoadOrdersForRestaurantUseCase loadOrdersForRestaurantUseCase;

    public OrderController(PlaceOrderUseCase placeOrderUseCase,AcceptOrderUseCase acceptOrderUseCase,RejectOrderUseCase rejectOrderUseCase,MarkOrderReadyUseCase markOrderReadyUseCase,CreatePaymentLinkUseCase createPaymentLinkUseCase,LoadOrdersForRestaurantUseCase loadOrdersForRestaurantUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
        this.acceptOrderUseCase = acceptOrderUseCase;
        this.rejectOrderUseCase = rejectOrderUseCase;
        this.markOrderReadyUseCase = markOrderReadyUseCase;
        this.createPaymentLinkUseCase = createPaymentLinkUseCase;
        this.loadOrdersForRestaurantUseCase = loadOrdersForRestaurantUseCase;
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
        acceptOrderUseCase.acceptOrder(new AcceptOrderCommand(orderId));
        log.info("Order {} accepted", orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/reject")
    public ResponseEntity<Void> rejectOrder(@PathVariable UUID orderId, @RequestBody RejectOrderRequest request) {
        RejectOrderCommand command = new RejectOrderCommand(orderId, request.reason());
        rejectOrderUseCase.rejectOrder(command);
        log.info("Order {} rejected with reason: {}", orderId, request.reason());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/ready")
    public ResponseEntity<Void> markOrderReady(@PathVariable UUID orderId) {
        markOrderReadyUseCase.markOrderReady(new MarkOrderReadyCommand(orderId));
        log.info("Order {} marked as ready", orderId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<String> createPaymentLink(@PathVariable UUID orderId) {
        String url = createPaymentLinkUseCase.createPaymentLink(orderId);
        return ResponseEntity.ok(url);
    }

    /**
     * Returns pending orders for the owner’s restaurant.
     * The owner is authenticated and their restaurantId is retrieved from the request.
     */
    @GetMapping("/{restaurantId}/pending")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<List<Order>> getPendingOrdersForRestaurant(
            @PathVariable UUID restaurantId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        // also validate that this restaurantId belongs to the owner (optional security check)
        List<Order> orders = loadOrdersForRestaurantUseCase.findOrdersByRestaurantAndStatus(
                restaurantId,
                OrderStatus.PLACED
        );
        return ResponseEntity.ok(orders);
    }
}
