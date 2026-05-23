package project.commercePJT.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.dto.ItemDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static project.commercePJT.dto.CategoryDto.*;
import static project.commercePJT.dto.ItemDto.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired CategoryService categoryService;
    @Autowired EntityManager em;

    @Test
    void 상품_등록_성공() {
        //given
        CategoryRequestDto categoryRequestDto1 = getCategoryRequestDto("clothes");
        Long categoryId = categoryService.createCategory(categoryRequestDto1);
        CategoryResponseDto category = categoryService.findCategory(categoryId);

        ItemRequestDto itemDto = getItemRequestDto(category);

        //when
        Long itemId = itemService.saveItem(itemDto);

        //then
        ItemResponseDto findItem = itemService.findItem(itemId);
        assertThat(findItem.getName()).isEqualTo(itemDto.getName());
    }

    @Test
    void 상품_정보_수정_성공() {
        //given
        CategoryRequestDto categoryRequestDto1 = getCategoryRequestDto("clothes");
        Long categoryId1 = categoryService.createCategory(categoryRequestDto1);
        CategoryResponseDto category1 = categoryService.findCategory(categoryId1);

        ItemRequestDto itemDto = getItemRequestDto(category1);
        Long itemId = itemService.saveItem(itemDto);

        //when
        CategoryRequestDto categoryRequestDto2 = getCategoryRequestDto("etc");
        Long categoryId2 = categoryService.createCategory(categoryRequestDto2);
        CategoryResponseDto category2 = categoryService.findCategory(categoryId2);
        ItemRequestDto newItemDto = new ItemRequestDto("pants", 20, 5000L, category2.getId());
        itemService.updateItem(itemId, newItemDto);

        em.flush();
        em.clear();

        //then
        ItemResponseDto findItem = itemService.findItem(itemId);
        assertThat(findItem.getName()).isEqualTo("pants");
    }

    private static ItemRequestDto getItemRequestDto(CategoryResponseDto category) {
        ItemRequestDto itemDto = new ItemRequestDto("t-shirts", 10, 10000L, category.getId());
        return itemDto;
    }

    private static CategoryRequestDto getCategoryRequestDto(String name) {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(name);
        return categoryRequestDto;
    }
}