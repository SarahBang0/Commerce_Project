package project.commercePJT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.commercePJT.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
