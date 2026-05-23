package project.commercePJT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.commercePJT.domain.Order;
import project.commercePJT.domain.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findByUserId(Long userId);

    @Query("select o from ORDERS o " +
            "join fetch o.user " +
            "join fetch o.orderItems oi " +
            "join fetch oi.item " +
            "where o.user.id = :userId")
    List<Order> findAllWithUserItem(@Param("userId") Long userId);
}
