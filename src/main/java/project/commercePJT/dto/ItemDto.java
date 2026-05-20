package project.commercePJT.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.item.Item;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ItemDto {

    @NotBlank(message = "품명은 필수 입력값입니다.")
    private String name;

    @NotNull(message = "재고 수량은 필수 입력값입니다.")
    private int quantity;

    @NotNull(message = "가격은 필수 입력값입니다.")
    private Long price;

    private String categoryName;

    public ItemDto(Item item) {
        this.name = item.getName();
        this.quantity = item.getStock_quantity();
        this.price = item.getPrice();
        this.categoryName = (item.getCategory() != null) ? item.getCategory().getName() : "미분류";
    }
}
