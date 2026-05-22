package project.commercePJT.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.*;
import project.commercePJT.domain.item.Category;
import project.commercePJT.domain.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired OrderItemRepository orderItemRepository;
    @Autowired OrderRepository orderRepository;

    @Test
    void 주문_성공() {
        //given
        User user = getUser();
        userRepository.save(user);
        Category category = getCategory();
        categoryRepository.save(category);
        Item item = getItem(category);
        itemRepository.save(item);
        OrderItem orderItem1 = OrderItem.createOrderItem(3, item);
        OrderItem orderItem2 = OrderItem.createOrderItem(2, item);
        List<OrderItem> orderItemList = List.of(orderItem1, orderItem2);

        Order order = Order.createOrder(user, orderItemList);

        //when
        orderRepository.save(order);

        //then
        Optional<Order> findOrder = orderRepository.findById(order.getId());
        assertThat(order.getTotalPrice()).isEqualTo(50000L);
        assertThat(findOrder.get()).isEqualTo(order);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    void 주문_취소_성공() {
        //given
        User user = getUser();
        userRepository.save(user);
        Category category = getCategory();
        categoryRepository.save(category);
        Item item = getItem(category);
        itemRepository.save(item);
        OrderItem orderItem1 = OrderItem.createOrderItem(3, item);
        OrderItem orderItem2 = OrderItem.createOrderItem(2, item);
        List<OrderItem> orderItemList = List.of(orderItem1, orderItem2);

        Order order = Order.createOrder(user, orderItemList);
        orderRepository.save(order);

        //when
        Order findOrder = orderRepository.findById(order.getId()).get();
        order.cancel();
        orderRepository.delete(order);

        //then
        assertThat(item.getStock_quantity()).isEqualTo(10);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    void 재고_부족() {
        //given
        User user = getUser();
        userRepository.save(user);
        Category category = getCategory();
        categoryRepository.save(category);
        Item item = getItem(category);
        itemRepository.save(item);

        //when & then
        assertThrows(IllegalStateException.class,
                ()-> OrderItem.createOrderItem(11, item));
    }

    private static Item getItem(Category category) {
        Item item = Item.createItem("t-shirt", 10, 10000, category);
        return item;
    }

    private static Category getCategory() {
        Category category = Category.createCategory("clothes");
        return category;
    }

    private static User getUser() {
        Address address = Address.createAddress("home", "seoul", "gangnam", "54321");
        User user = User.createUser("Java", "java@gmail.com", "1234", address);
        return user;
    }

}