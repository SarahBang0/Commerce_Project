package project.commercePJT.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Common 공통 에러
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력값입니다."),

    // User (회원 관련)
    USER_NOT_FOUND(404, "U001", "해당 회원은 존재하지 않습니다."),
    DUPLICATE_EMAIL(409, "U002", "이미 사용 중인 이메일입니다."),

    // Item (상품 관련)
    ITEM_NOT_FOUND(404, "I001", "해당 상품은 존재하지 않습니다."),

    // Category (카테고리 관련)
    CATEGORY_NOT_FOUND(404, "CA001", "해당 카테고리는 존재하지 않습니다."),

    // Order (주문 관련)
    ORDER_NOT_FOUND(404, "O001", "해당 주문은 존재하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;
}
