package be.kdg.backend.order.port.in;

import be.kdg.backend.order.port.in.request.*;

public interface BasketUseCase {
    void addItem(AddItemToBasketCommand command);
    void removeItem(RemoveItemFromBasketCommand command);
    void lockBasket(LockBasketCommand command);
    void checkout(CheckoutBasketCommand command);
    void updateItem(UpdateItemQuantityCommand command);
}
