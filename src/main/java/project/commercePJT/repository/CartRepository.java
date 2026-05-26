package project.commercePJT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.commercePJT.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
