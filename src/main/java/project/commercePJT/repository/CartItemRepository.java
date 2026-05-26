package project.commercePJT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.commercePJT.domain.CartItem;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);
}
