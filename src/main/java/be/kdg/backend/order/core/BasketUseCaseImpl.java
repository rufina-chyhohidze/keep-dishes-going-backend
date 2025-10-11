package be.kdg.backend.order.core;

import be.kdg.backend.order.domain.Basket;
import be.kdg.backend.order.port.in.BasketUseCase;
import be.kdg.backend.order.port.in.request.*;
import be.kdg.backend.order.port.out.LoadBasketPort;
import be.kdg.backend.order.port.out.SaveBasketPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@Transactional
public class BasketUseCaseImpl  implements BasketUseCase {
    Logger logger = LoggerFactory.getLogger(BasketUseCaseImpl.class);
    private final LoadBasketPort loadBasketPort;
    private final SaveBasketPort saveBasketPort;

    public BasketUseCaseImpl(LoadBasketPort loadBasketPort, SaveBasketPort saveBasketPort) {
        this.loadBasketPort = loadBasketPort;
        this.saveBasketPort = saveBasketPort;
    }

    @Override
    public void addItem(AddItemToBasketCommand cmd) {
        Basket basket = loadBasketPort.load(cmd.basketId())
                .orElse(new Basket(cmd.basketId(), cmd.restaurantId()));
        basket.addItem(cmd.dishId(), cmd.dishName(), BigDecimal.valueOf(cmd.price()), cmd.quantity());
        logger.info("Adding item to basket with id " + cmd.basketId());
        saveBasketPort.save(basket);
    }

    @Override
    public void updateItem(UpdateItemQuantityCommand cmd) {
        Basket basket = loadBasketPort.load(cmd.basketId())
                .orElseThrow(() -> new IllegalStateException("Basket not found"));
        basket.updateQuantity(cmd.dishId(), cmd.quantity());
        logger.info("Updating item to basket {}", cmd.basketId());
        saveBasketPort.save(basket);
    }

    @Override
    public void removeItem(RemoveItemFromBasketCommand cmd) {
        Basket basket = loadBasketPort.load(cmd.basketId())
                .orElseThrow(() -> new IllegalStateException("Basket not found"));
        basket.removeItem(cmd.dishId());
        logger.info("Removing item from basket {}", cmd.basketId());
        saveBasketPort.save(basket);
    }

    @Override
    public void lockBasket(LockBasketCommand cmd) {
        Basket basket = loadBasketPort.load(cmd.basketId())
                .orElseThrow(() -> new IllegalStateException("Basket not found"));
        basket.lock();
        logger.info("Locking basket {}", cmd.basketId());
        saveBasketPort.save(basket);
    }

    @Override
    public void checkout(CheckoutBasketCommand cmd) {
        Basket basket = loadBasketPort.load(cmd.basketId())
                .orElseThrow(() -> new IllegalStateException("Basket not found"));
        basket.checkout();
        saveBasketPort.save(basket);
        logger.info("Checking out basket {}", cmd.basketId());
        // TODO: publish OrderCreatedEvent here
    }
}
