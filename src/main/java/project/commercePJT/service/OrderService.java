package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.*;
import project.commercePJT.domain.item.Item;
import project.commercePJT.exception.ErrorCode;
import project.commercePJT.exception.ResourceNotFoundException;
import project.commercePJT.repository.ItemRepository;
import project.commercePJT.repository.OrderRepository;
import project.commercePJT.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static project.commercePJT.dto.OrderDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    // 주문 생성
    @Transactional
    public Long createOrder(OrderRequestDto orderRequestDto) {
        User findUser = validateUserExists(orderRequestDto.getUserId());

        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderItemRequestDto dto : orderRequestDto.getOrderItemRequestDtoList()) {
            Item item = validateItemExists(dto.getItemId());
            OrderItem orderItem = OrderItem.createOrderItem(dto.getQuantity(), item);
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(findUser, orderItemList);

        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    // 장바구니에서 최종 주문
    @Transactional
    public Long orderFromCart(Long userId) {
        User findUser = validateUserExists(userId);
        Cart cart = findUser.getCart();
        List<CartItem> cartItems = cart.getCartItems();

        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem cartItem : cartItems) {
            Item item = validateItemExists(cartItem.getItem().getId());
            OrderItem orderItem = OrderItem.createOrderItem(cartItem.getCount(), item);
            orderItems.add(orderItem);
        }
        Order order = Order.createOrder(findUser, orderItems);
        Order savedOrder = orderRepository.save(order);

        cart.clearCart();

        return savedOrder.getId();
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = validateOrderExists(orderId);
        order.cancel();
    }

    // 주문 목록 조회
    public List<OrderResponseDto> findOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    // 회원별 주문 목록 조회
    public List<OrderResponseDto> findOrdersByUserId(Long userId) {
        validateUserExists(userId);
        return orderRepository.findAllWithUserItem(userId).stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    // 주문 상세 조회
    public OrderResponseDto findOrder(Long orderId) {
        Order order = validateOrderExists(orderId);
        return new OrderResponseDto(order);
    }



    // 주문 존재 여부 검증 로직
    private Order validateOrderExists(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("해당 주문이 존재하지 않습니다. Id=" + orderId)
        );
        return order;
    }


    // 상품 존재 여부 검증 로직
    private Item validateItemExists(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException(itemId, ErrorCode.ITEM_NOT_FOUND)
        );
        return item;
    }


    // 회원 존재 여부 검증 로직
    private User validateUserExists(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException(userId, ErrorCode.USER_NOT_FOUND));
        return findUser;
    }



}

