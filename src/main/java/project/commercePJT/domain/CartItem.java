package project.commercePJT.domain;

import jakarta.persistence.*;
import lombok.Getter;
import project.commercePJT.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CartItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartItem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    //==생성 메서드==//
    public static CartItem createCartItem(Cart cart, Item item, int count) {
        CartItem cartItem = new CartItem();
        cartItem.cart = cart;
        cartItem.item = item;
        cartItem.count = count;

        cart.getCartItems().add(cartItem);
        return cartItem;
    }

    public void addCount(int count) {
        this.count += count;
    }
}
