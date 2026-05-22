package project.commercePJT.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.item.Item;

@Entity(name = "ORDERITEMS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    //==생성 메서드==//
    public static OrderItem createOrderItem(int quantity, Item item) {
        OrderItem orderItem = new OrderItem();
        orderItem.quantity = quantity;
        orderItem.price = item.getPrice();
        orderItem.item = item;
        item.removeStock(quantity);
        return orderItem;
    }

    //==변경 메서드==//
    public void setOrder(Order order) {
        this.order = order;
    }

    //==재고 원복==//
    public void cancel() {
        getItem().addStock(quantity);
    }

    public void updateOrderItem(int quantity) {
        this.quantity = quantity;
    }
}
