package project.commercePJT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.commercePJT.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
