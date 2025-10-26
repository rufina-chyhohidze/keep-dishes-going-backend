package be.kdg.backend.order.adapter;

import be.kdg.backend.order.port.in.ConfirmOrderPaymentUseCase;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/webhooks/stripe")
public class StripeWebhookController {
    private static final Logger log = LoggerFactory.getLogger(StripeWebhookController.class);

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final ConfirmOrderPaymentUseCase confirmOrderPaymentUseCase;
    public StripeWebhookController(ConfirmOrderPaymentUseCase confirmOrderPaymentUseCase) {
        this.confirmOrderPaymentUseCase = confirmOrderPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            log.error("Stripe signature verification failed.", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if ("checkout.session.completed".equals(event.getType())) {
            JsonObject json = JsonParser.parseString(payload).getAsJsonObject();
            String sessionId = json.getAsJsonObject("data").getAsJsonObject("object").get("id").getAsString();

            try {
                Session session = Session.retrieve(sessionId);
                String orderId = session.getMetadata().get("orderId");
                confirmOrderPaymentUseCase.confirmPayment(UUID.fromString(orderId));
                log.info("------Payment confirmed-------- for order {}", orderId);
            } catch (Exception e) {
                log.error("Error processing payment webhook", e);
            }
        }
        return ResponseEntity.ok().build();
    }
}
