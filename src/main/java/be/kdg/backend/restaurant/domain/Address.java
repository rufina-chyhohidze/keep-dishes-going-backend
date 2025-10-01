package be.kdg.backend.restaurant.domain;

public record Address(
        String streetName,
        String streetNumber,
        String postalCode,
        String city,
        String country
) {
}
