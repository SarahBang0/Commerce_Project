package project.commercePJT.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CartDto {


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class CartRequestDto {
        private Long userId;
        private List<CartItemRequestDto> cartItemRequestDtos;
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class CartItemRequestDto {
        private Long itemId;
        private int count;

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class CartResponseDto {

        private Long cartId;
        private String userName;
        private String userEmail;
        private List<CartItemResponseDto> cartItems = new ArrayList<>();
        private Long totalPrice;

        public CartResponseDto(Cart cart) {
            this.cartId = cart.getId();
            this.userName = cart.getUser().getName();
            this.userEmail = cart.getUser().getEmail();
            for (CartItem cartItem : cart.getCartItems()) {
                CartItemResponseDto cartItemResponseDto = new CartItemResponseDto(cartItem);
                this.cartItems.add(cartItemResponseDto);
            }
            this.totalPrice = cart.getTotalPrice();
        }

    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class CartItemResponseDto {
        private Long itemId;
        private String itemName;
        private Long price; //단가
        private int count; // 주문 수량
        private Long totalItemPrice; // 단가 * 수량

        public CartItemResponseDto(CartItem cartItem) {
            this.itemId = cartItem.getItem().getId();
            this.itemName = cartItem.getItem().getName();
            this.price = cartItem.getItem().getPrice();
            this.count = cartItem.getCount();
            this.totalItemPrice = this.price * this.count;

        }
    }
}
