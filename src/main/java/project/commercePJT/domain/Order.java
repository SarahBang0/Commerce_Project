package project.commercePJT.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Long totalPrice;

    private LocalDateTime order_date;

    @Enumerated (EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();



    //==생성 메서드==//
    public static Order createOrder(User user, OrderItem... orderItems) {
        Order order = new Order();
        order.setUser(user);

        long total = 0;
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
            total += orderItem.getPrice() * orderItem.getQuantity();
        }

        order.totalPrice = total;
        order.order_date = LocalDateTime.now();
        order.orderStatus = OrderStatus.PAID;

        return order;
    }

    //==변경 메서드==//
    private void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    private void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    private void remove() {
        this.user.getOrders().remove(this);
    }

    private void setTotalPrice(OrderItem orderItem) {
        this.totalPrice = orderItem.getPrice() * orderItem.getQuantity();
    }
}
