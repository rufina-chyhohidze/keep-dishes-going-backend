package be.kdg.backend.order.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {

    /**
     * Fetches order older than a given time and still in PLACED state
     * @param threshold
     * @return
     */
    @Query("""
        SELECT o FROM OrderJpaEntity o
        WHERE o.status = 'PLACED' AND o.createdAt < :threshold
    """)
    List<OrderJpaEntity> findPendingOlderThan(Instant threshold);
}
