package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.Order;
import project.commercePJT.domain.OrderItem;
import project.commercePJT.domain.User;
import project.commercePJT.domain.item.Item;
import project.commercePJT.dto.OrderDto;
import project.commercePJT.repository.ItemRepository;
import project.commercePJT.repository.OrderItemRepository;
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
        User findUser = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(()-> new IllegalStateException("해당 회원이 존재하지 않습니다. Id=" + orderRequestDto.getUserId()));

        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderItemRequestDto dto : orderRequestDto.getOrderItemRequestDtoList()) {
            Item item = itemRepository.findById(dto.getItemId()).orElseThrow(
                    () -> new IllegalStateException("해당 상품이 존재하지 않습니다. Id=" + dto.getItemId())
            );
            OrderItem orderItem = OrderItem.createOrderItem(dto.getQuantity(), item);
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(findUser, orderItemList);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("해당 주문이 존재하지 않습니다. Id=" + orderId)
        );
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
        User findUser = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalStateException("해당 회원이 존재하지 않습니다. Id=" +userId));
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    // 주문 상세 조회
    public OrderResponseDto findOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("해당 주문이 존재하지 않습니다. Id=" + orderId)
        );
        return new OrderResponseDto(order);
    }
}

