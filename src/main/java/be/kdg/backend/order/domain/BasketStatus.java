package be.kdg.backend.order.domain;

public enum BasketStatus {
    ACTIVE,
    LOADED,
    CHECKED_OUT, //after order creation
    CANCELED,
    LOCKED
}
