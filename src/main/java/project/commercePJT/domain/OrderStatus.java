package project.commercePJT.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PAID("결제 완료"),
    SHIPPING("배송중"),
    DELIVERED("배송 완료"),
    CANCELLED("주문 취소");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
