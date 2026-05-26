package project.commercePJT.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.Address;
import project.commercePJT.domain.Order;
import project.commercePJT.domain.OrderItem;
import project.commercePJT.domain.OrderStatus;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderRequestDto {
        private Long userId;
        private List<OrderItemRequestDto> orderItemRequestDtoList;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderItemRequestDto {
        private Long itemId;
        private int quantity;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderResponseDto {
        private Long orderId;
        private Long userId;
        private String userName;
        private String userEmail;
        private Address address;
        private String itemName;
        private String orderDate;
        private OrderStatus orderStatus;
        private List<OrderItemResponseDto> orderItems = new ArrayList<>();
        private Long totalPrice;
        private int totalCount = 0;

        public OrderResponseDto(Order order) {
            this.orderId = order.getId();
            this.userId = order.getUser().getId();
            this.userName = order.getUser().getName();
            this.userEmail = order.getUser().getEmail();
            this.address = order.getUser().getAddress();
            this.itemName = order.getOrderItems().get(0).getItem().getName();
            this.orderDate = order.getOrder_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.orderStatus = order.getOrderStatus();
            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto(orderItem);
                this.orderItems.add(orderItemResponseDto);
            }
            this.totalPrice = order.getTotalPrice();
            for (OrderItem orderItem : order.getOrderItems()) {
                totalCount += orderItem.getQuantity();
            }
        }

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderItemResponseDto {
        private String itemName;
        private Long orderPrice;
        private int orderQuantity;
        private Long totalPrice;

        public OrderItemResponseDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getPrice();
            this.orderQuantity = orderItem.getQuantity();
            this.totalPrice = this.orderPrice * this.orderQuantity;
        }
    }
}
