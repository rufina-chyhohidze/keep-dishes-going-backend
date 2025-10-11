package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.adapter.out.mapper.BasketMapper;
import be.kdg.backend.order.domain.Basket;
import be.kdg.backend.order.port.out.LoadBasketPort;
import be.kdg.backend.order.port.out.SaveBasketPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BasketJpaAdapter implements LoadBasketPort, SaveBasketPort {
    private final BasketJpaRepository basketJpaRepository;

    public BasketJpaAdapter(BasketJpaRepository basketJpaRepository) {
        this.basketJpaRepository = basketJpaRepository;
    }

    @Override
    public Optional<Basket> load(UUID basketId) {
        return basketJpaRepository.findById(basketId).map(BasketMapper::toDomain);
    }

    @Override
    public void save(Basket basket) {
        basketJpaRepository.save(BasketMapper.toEntity(basket));
    }
}
