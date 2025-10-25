package be.kdg.backend.order.adapter.out;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {

    @Query("""
    SELECT o FROM OrderJpaEntity o
    LEFT JOIN FETCH o.orderLines
    WHERE o.status = 'PLACED' AND o.createdAt < :threshold
""")
    List<OrderJpaEntity> findPendingOlderThan(@Param("threshold") Instant threshold);

    // eagerly fetch orderLines
    @EntityGraph(attributePaths = "orderLines")
    Optional<OrderJpaEntity> findByOrderId(UUID orderId);
}

