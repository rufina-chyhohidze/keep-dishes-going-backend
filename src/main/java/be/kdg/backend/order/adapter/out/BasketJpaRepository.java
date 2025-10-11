package be.kdg.backend.order.adapter.out;

import be.kdg.backend.order.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketJpaRepository extends JpaRepository<BasketJpaEntity, UUID> {
}
