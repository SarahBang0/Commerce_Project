package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.Cart;
import project.commercePJT.domain.CartItem;
import project.commercePJT.domain.User;
import project.commercePJT.domain.item.Item;
import project.commercePJT.exception.ErrorCode;
import project.commercePJT.exception.ResourceNotFoundException;
import project.commercePJT.repository.CartItemRepository;
import project.commercePJT.repository.CartRepository;
import project.commercePJT.repository.ItemRepository;
import project.commercePJT.repository.UserRepository;


import java.util.List;
import java.util.Optional;

import static project.commercePJT.dto.CartDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    // 장바구니에 상품 담기
    @Transactional
    public Long addCart(Long userId, CartItemRequestDto cartItemRequestDto) {
        // 유저와 상품 존재 여부 확인
        User findUser = validateUserExists(userId);
        Item item = validateItemExists(cartItemRequestDto.getItemId());

        // 유저의 장바구니 꺼내오기
        Cart cart = findUser.getCart();

        // 장바구니에 해당 상품이 있는지 확인
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        if (savedCartItem != null) {
            savedCartItem.addCount(cartItemRequestDto.getCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemRequestDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    // 장바구니 목록 조회
    public List<CartResponseDto> findCarts() {
        return cartRepository.findAll().stream()
                .map(CartResponseDto::new)
                .toList();
    }

    // 회원별 장바구니 목록 조회
    public CartResponseDto findCartByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null || user.getCart() == null) {
            return null;
        } else {
            return new CartResponseDto(user.getCart());
        }
    }

    // 회원 존재 여부 검증 로직
    private User validateUserExists(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException(userId, ErrorCode.USER_NOT_FOUND));
        return findUser;
    }

    // 상품 존재 여부 검증 로직
    private Item validateItemExists(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException(itemId, ErrorCode.ITEM_NOT_FOUND)
        );
        return item;
    }
}
