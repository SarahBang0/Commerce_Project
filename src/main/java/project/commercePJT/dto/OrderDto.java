package project.commercePJT.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @AllArgsConstructor
    public static class OrderRequestDto {
        private Long userId;
        private List<OrderItemRequestDto> orderItemRequestDtoList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OrderItemRequestDto {
        private Long itemId;
        private int quantity;

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class OrderResponseDto {
        private Long orderId;
        private String userName;
        private String orderDate;
        private OrderStatus orderStatus;
        private List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();

        public OrderResponseDto(Order order) {
            this.orderId = order.getId();
            this.userName = order.getUser().getName();
            this.orderDate = order.getOrder_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.orderStatus = order.getOrderStatus();
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto(orderItem);
                this.orderItemResponseDtos.add(orderItemResponseDto);
            }
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
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
