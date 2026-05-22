package project.commercePJT.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.Address;
import project.commercePJT.domain.Order;
import project.commercePJT.domain.OrderItem;
import project.commercePJT.domain.OrderStatus;
import project.commercePJT.domain.item.Item;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderRequestDto {
        private Long userId;
        private List<OrderItemRequestDto> orderItemRequestDtoList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderItemRequestDto {
        private Long itemId;
        private int quantity;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderResponseDto {
        private Long orderId;
        private String userName;
        private Address address;
        private String orderDate;
        private OrderStatus orderStatus;
        private List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();
        private Long totalPrice;

        public OrderResponseDto(Order order) {
            this.orderId = order.getId();
            this.userName = order.getUser().getName();
            this.address = order.getUser().getAddress();
            this.orderDate = order.getOrder_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.orderStatus = order.getOrderStatus();
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto(orderItem);
                this.orderItemResponseDtos.add(orderItemResponseDto);
            }
            this.totalPrice = order.getTotalPrice();
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderItemResponseDto {
        private String itemName;
        private Long orderPrice;
        private int orderQuantity;

        public OrderItemResponseDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getPrice();
            this.orderQuantity = orderItem.getQuantity();
        }
    }
}
