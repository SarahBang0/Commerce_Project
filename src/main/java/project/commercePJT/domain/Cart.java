package project.commercePJT.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();


    public void setUser(User user) {
        this.user = user;
        if (user.getCart() != this) {
            user.setCart(this);
        }
    }

    public Long getTotalPrice() {
        return cartItems.stream()
                .mapToLong(item -> item.getItem().getPrice() * item.getCount())
                .sum();
    }
}
