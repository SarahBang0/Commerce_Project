/*
package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.OrderItem;
import project.commercePJT.domain.item.Item;
import project.commercePJT.dto.OrderDto;
import project.commercePJT.repository.ItemRepository;
import project.commercePJT.repository.OrderItemRepository;

import java.util.List;

import static project.commercePJT.dto.OrderDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    // 주문 상품 등록
    @Transactional
    public Long saveOrderItem(Long itemId, OrderItemRequestDto dto) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalStateException("해당 상품이 존재하지 않습니다. Id=" + itemId)
        );
        OrderItem orderItem = OrderItem.createOrderItem(dto.getQuantity(), item);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return savedOrderItem.getId();
    }

    // 주문 상품 수정
    @Transactional
    public void updateOrderItem(Long orderItemId, OrderItemRequestDto dto) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(
                () -> new IllegalStateException("해당 주문 상품이 존재하지 않습니다. Id=" + orderItemId)
        );
        orderItem.updateOrderItem(dto.getQuantity());
    }

    // 주문 상품 삭제
    @Transactional
    public void removeOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(
                () -> new IllegalStateException("해당 주문 상품이 존재하지 않습니다. Id=" + orderItemId)
        );
        orderItemRepository.delete(orderItem);
    }

    // 주문 상품 목록 조회
    public List<OrderItemResponseDto> findOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(OrderItemResponseDto::new)
                .toList();
    }

    // 주문 상품 상세 조회
    public OrderItemResponseDto findOrderItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(
                () -> new IllegalStateException("해당 주문 상품이 존재하지 않습니다. Id=" + orderItemId)
        );
        return new OrderItemResponseDto(orderItem);
    }
}
*/
