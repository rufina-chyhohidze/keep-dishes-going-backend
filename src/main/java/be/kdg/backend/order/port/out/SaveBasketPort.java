package be.kdg.backend.order.port.out;

import be.kdg.backend.order.domain.Basket;

public interface SaveBasketPort {
    void save (Basket basket);
}
