package project.commercePJT.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Category;
import project.commercePJT.dto.ItemDto;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired CategoryService categoryService;
    @Autowired EntityManager em;

    @Test
    void 상품_등록_성공() {
        //given
        Long categoryId = categoryService.createCategory(getCategory());
        Category category = categoryService.findCategory(categoryId);
        ItemDto itemDto = getItemDto(category);

        //when
        Long itemId = itemService.saveItem(itemDto);

        //then
        ItemDto findItem = itemService.findItem(itemId);
        assertThat(findItem.getName()).isEqualTo(itemDto.getName());
    }

    private static ItemDto getItemDto(Category category) {
        ItemDto itemDto = new ItemDto("t-shirts", 10, 10000L, category);
        return itemDto;
    }

    private static Category getCategory() {
        Category category = Category.createCategory("clothes");
        return category;
    }

    @Test
    void 상품_정보_수정_성공() {

    }


}