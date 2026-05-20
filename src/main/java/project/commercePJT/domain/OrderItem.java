package project.commercePJT.domain;

import jakarta.persistence.*;
import lombok.Getter;
import project.commercePJT.domain.item.Item;

@Entity(name = "ORDERITEMS")
@Getter
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItem_id")
    private Long id;

    @Column(name = "order_quantity")
    private int quantity;

    @Column(name = "order_price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

}
