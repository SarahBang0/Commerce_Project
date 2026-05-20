package project.commercePJT.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import project.commercePJT.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

@Entity (name = "ITEMS")
@Getter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    private int stock_quantity;

    @Column(name = "item_price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

}
