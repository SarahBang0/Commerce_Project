package project.commercePJT.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity (name = "ITEMS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    private int stockQuantity;

    @Column(name = "item_price")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    //==생성 메서드==//
    public static Item createItem(String name, int stockQuantity, long price, Category category) {
        Item item = new Item();
        item.name = name;
        item.stockQuantity = stockQuantity;
        item.price = price;
        item.setCategory(category);
        return item;
    }

    //==변경 메서드==//
    public void changeItem(String name, int stockQuantity, long price, Category category) {
        if(name != null) {
            this.name = name;
        }
         if(stockQuantity != -1) {
             this.stockQuantity = stockQuantity;
         }
         if(price != -1) {
             this.price = price;
         }
         if(category != null) {
             this.category = category;
         }
    }


    private void setCategory(Category category) {
        this.category = category;
        if(category != null) {
            category.getItems().add(this);
        }
    }

    private void remove() {
        this.category.getItems().remove(this);
    }

    //==재고 관리 메서드==//
    public void removeStock(int quantity) {
        int restStock = stockQuantity - quantity;
        if(restStock < 0) {
            throw new IllegalStateException("재고 부족");
        }
        this.stockQuantity = restStock;
    }

    public void addStock(int quantity) {
        if(quantity >= 0) {
            stockQuantity += quantity;
        } else {
            throw new IllegalStateException("수량 오류");
        }
    }
}
