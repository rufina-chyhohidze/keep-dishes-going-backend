package be.kdg.backend.order.adapter.in;


import be.kdg.backend.order.port.in.BasketUseCase;
import be.kdg.backend.order.port.in.request.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {
    Logger logger = LoggerFactory.getLogger(BasketController.class);
    private final BasketUseCase basketUseCase;

    public BasketController(BasketUseCase basketUseCase) {
        this.basketUseCase = basketUseCase;
    }

    @PostMapping("/add")
    public void addItem(@RequestBody AddItemToBasketCommand cmd) {
        basketUseCase.addItem(cmd);
    }

    @PatchMapping("/update")
    public void updateItem(@RequestBody UpdateItemQuantityCommand cmd) {
        basketUseCase.updateItem(cmd);
    }

    @DeleteMapping("/{basketId}/{dishId}")
    public void removeItem(@PathVariable UUID basketId, @PathVariable UUID dishId) {
        basketUseCase.removeItem(new RemoveItemFromBasketCommand(basketId, dishId));
    }

    @PostMapping("/{basketId}/lock")
    public void lock(@PathVariable UUID basketId) {
        basketUseCase.lockBasket(new LockBasketCommand(basketId));
    }

    @PostMapping("/{basketId}/checkout")
    public void checkout(@PathVariable UUID basketId, @RequestBody CheckoutBasketCommand cmd) {
        basketUseCase.checkout(cmd);
    }
}
