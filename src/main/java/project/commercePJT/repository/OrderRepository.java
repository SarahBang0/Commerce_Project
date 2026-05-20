package project.commercePJT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.commercePJT.domain.Order;
import project.commercePJT.domain.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
