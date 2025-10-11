package be.kdg.backend.order.port.in.request;

import java.util.UUID;

public record CheckoutBasketCommand(UUID basketId,
                                    String name,
                                    String email,
                                    String street,
                                    String number,
                                    String postalCode,
                                    String city,
                                    String country) {
}
