package project.commercePJT.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.commercePJT.domain.item.Category;


public class CategoryDto {


    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class CategoryRequestDto {
        @NotBlank(message = "품명은 필수 입력값입니다.")
        private String name;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class CategoryResponseDto {

        private Long id;
        private String name;

        public CategoryResponseDto(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }

    }


}
