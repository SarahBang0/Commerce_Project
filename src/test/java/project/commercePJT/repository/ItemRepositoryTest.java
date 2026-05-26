package project.commercePJT.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Category;
import project.commercePJT.domain.item.Item;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired CategoryRepository categoryRepository;
    @Autowired ItemRepository itemRepository;

    @Test
    void 상품_생성_성공() {
        //given
        Category category = getCategory();
        categoryRepository.save(category);
        Item item = getItem(category);

        //when
        itemRepository.save(item);

        //then
        Item findItem = itemRepository.findByName(item.getName()).get(0);
        Assertions.assertThat(findItem).isEqualTo(item);
        Assertions.assertThat(itemRepository.findByCategoryId(category.getId()).get(0)).isEqualTo(findItem);
    }

    private static Item getItem(Category category) {
        Item item = Item.createItem("t-shirt", 10, 10000, category);
        return item;
    }

    private static Category getCategory() {
        Category category = Category.createCategory("clothes");
        return category;
    }
}