package project.commercePJT.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.item.Item;


public class ItemDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ItemRequestDto {

        @NotBlank(message = "품명은 필수 입력값입니다.")
        private String name;

        @NotNull(message = "재고 수량은 필수 입력값입니다.")
        private int quantity;

        @NotNull(message = "가격은 필수 입력값입니다.")
        private Long price;

        private Long categoryId;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ItemResponseDto {

        private Long itemId;
        private String name;
        private int quantity;
        private Long price;
        private Long categoryId;
        private String categoryName;

        public ItemResponseDto(Item item) {
            this.itemId = item.getId();
            this.name = item.getName();
            this.quantity = item.getStockQuantity();
            this.price = item.getPrice();
            this.categoryId = item.getCategory().getId();
            this.categoryName = item.getCategory().getName();
        }

    }


}
