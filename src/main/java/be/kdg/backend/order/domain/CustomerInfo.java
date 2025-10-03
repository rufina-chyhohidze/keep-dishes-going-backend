package be.kdg.backend.order.domain;

public record CustomerInfo(String name,
                           String email,
                           String street,
                           String number,
                           String postalCode,
                           String city,
                           String country) {
}
