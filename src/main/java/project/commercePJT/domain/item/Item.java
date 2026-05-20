package project.commercePJT.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

@Entity (name = "ITEMS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    //==생성 메서드==//
    public static Item createItem(String name, int stock_quantity, long price, Category category) {
        Item item = new Item();
        item.name = name;
        item.stock_quantity = stock_quantity;
        item.price = price;
        item.setCategory(category);
        return item;
    }

    //==변경 메서드==//
    private void setCategory(Category category) {
        this.category = category;
        if(category != null) {
            category.getItems().add(this);
        }
    }

    private void remove() {
        this.category.getItems().remove(this);
    }

    public void removeStock(int quantity) {
        int restStock = stock_quantity - quantity;
        if(restStock < 0) {
            throw new IllegalStateException("재고 부족");
        }
        this.stock_quantity = restStock;
    }

    public void addStock(int quantity) {
        if(quantity >= 0) {
            stock_quantity += quantity;
        } else {
            throw new IllegalStateException("수량 오류");
        }
    }
}
