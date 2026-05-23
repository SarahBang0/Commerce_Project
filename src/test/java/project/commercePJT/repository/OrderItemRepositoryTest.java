package project.commercePJT.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.OrderItem;
import project.commercePJT.domain.item.Category;
import project.commercePJT.domain.item.Item;

import java.util.Optional;

@SpringBootTest
@Transactional
class OrderItemRepositoryTest {

    @Autowired CategoryRepository categoryRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired OrderItemRepository orderItemRepository;


    @Test
    void 주문_상품_생성_성공() {
        //given
        Category category = getCategory();
        categoryRepository.save(category);
        Item item = getItem(category);
        itemRepository.save(item);

        OrderItem orderItem = OrderItem.createOrderItem(3, item);

        //when
        orderItemRepository.save(orderItem);

        //then
        Optional<OrderItem> findOrderItem = orderItemRepository.findById(orderItem.getId());
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(7);
    }

    @Test
    void 주문_상품_취소_성공() {
        //given
        Category category = getCategory();
        categoryRepository.save(category);
        Item item = getItem(category);
        itemRepository.save(item);

        OrderItem orderItem = OrderItem.createOrderItem(3, item);
        orderItemRepository.save(orderItem);

        //when
        orderItem.cancel();
        orderItemRepository.delete(orderItem);

        //then
        Optional<OrderItem> findOrderItem = orderItemRepository.findById(orderItem.getId());
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(10);
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