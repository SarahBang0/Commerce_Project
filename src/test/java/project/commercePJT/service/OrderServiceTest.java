package project.commercePJT.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.Address;
import project.commercePJT.domain.OrderStatus;
import project.commercePJT.dto.CategoryDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static project.commercePJT.dto.ItemDto.*;
import static project.commercePJT.dto.OrderDto.*;
import static project.commercePJT.dto.UserDto.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired UserService userService;
    @Autowired CategoryService categoryService;
    @Autowired ItemService itemService;
    @Autowired OrderService orderService;
    @Autowired
    EntityManager em;



    @Test
    void 주문_성공() {
        //given
        Long userId = userService.joinUser(getUserJoinRequestDto());

        CategoryDto.CategoryRequestDto categoryRequestDto1 = getCategoryRequestDto("clothes");
        Long categoryId = categoryService.createCategory(categoryRequestDto1);
        CategoryDto.CategoryResponseDto category = categoryService.findCategory(categoryId);

        ItemRequestDto itemDto1 = new ItemRequestDto("t-shirts", 20, 10000L, category.getId());
        ItemRequestDto itemDto2 = new ItemRequestDto("pants", 10, 15000L, category.getId());
        Long itemId1 = itemService.saveItem(itemDto1);
        Long itemId2 = itemService.saveItem(itemDto2);


        OrderItemRequestDto orderItemRequestDto1 = new OrderItemRequestDto(itemId1, 5);
        OrderItemRequestDto orderItemRequestDto2 = new OrderItemRequestDto(itemId2, 3);
        List<OrderItemRequestDto> orderItemRequestDtoList = List.of(orderItemRequestDto1, orderItemRequestDto2);

        //when
        OrderRequestDto orderRequestDto = new OrderRequestDto(userId, orderItemRequestDtoList);
        Long orderId = orderService.createOrder(orderRequestDto);

        //then
        OrderResponseDto findOrder = orderService.findOrder(orderId);
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(findOrder.getUserName()).isEqualTo("Spring");
        assertThat(findOrder.getTotalPrice()).isEqualTo(5*10000L + 3*15000L);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(2);

        assertThat(itemService.findItem(itemId1).getQuantity()).isEqualTo(15);
        assertThat(itemService.findItem(itemId2).getQuantity()).isEqualTo(7);

    }


    @Test
    void 주문_실패_재고_부족() {
        //given
        Long userId = userService.joinUser(getUserJoinRequestDto());

        CategoryDto.CategoryRequestDto categoryRequestDto1 = getCategoryRequestDto("clothes");
        Long categoryId = categoryService.createCategory(categoryRequestDto1);
        CategoryDto.CategoryResponseDto category = categoryService.findCategory(categoryId);

        ItemRequestDto itemDto1 = new ItemRequestDto("t-shirts", 20, 10000L, category.getId());
        Long itemId1 = itemService.saveItem(itemDto1);

        //when
        OrderItemRequestDto orderItemRequestDto1 = new OrderItemRequestDto(itemId1, 25);
        List<OrderItemRequestDto> orderItemRequestDtoList = List.of(orderItemRequestDto1);

        OrderRequestDto orderRequestDto = new OrderRequestDto(userId, orderItemRequestDtoList);

        //then
        assertThrows(IllegalStateException.class,
                ()-> orderService.createOrder(orderRequestDto));
    }

    @Test
    void 주문_취소_성공() {
        //given
        Long userId = userService.joinUser(getUserJoinRequestDto());

        CategoryDto.CategoryRequestDto categoryRequestDto1 = getCategoryRequestDto("clothes");
        Long categoryId = categoryService.createCategory(categoryRequestDto1);
        CategoryDto.CategoryResponseDto category = categoryService.findCategory(categoryId);

        ItemRequestDto itemDto1 = new ItemRequestDto("t-shirts", 20, 10000L, category.getId());
        ItemRequestDto itemDto2 = new ItemRequestDto("pants", 10, 15000L, category.getId());
        Long itemId1 = itemService.saveItem(itemDto1);
        Long itemId2 = itemService.saveItem(itemDto2);


        OrderItemRequestDto orderItemRequestDto1 = new OrderItemRequestDto(itemId1, 5);
        OrderItemRequestDto orderItemRequestDto2 = new OrderItemRequestDto(itemId2, 3);
        List<OrderItemRequestDto> orderItemRequestDtoList = List.of(orderItemRequestDto1, orderItemRequestDto2);

        OrderRequestDto orderRequestDto = new OrderRequestDto(userId, orderItemRequestDtoList);
        Long orderId = orderService.createOrder(orderRequestDto);

        //when
        orderService.cancelOrder(orderId);
        em.flush();
        em.clear();

        //then
        OrderResponseDto findOrder = orderService.findOrder(orderId);
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(itemService.findItem(itemId1).getQuantity()).isEqualTo(20);
        assertThat(itemService.findItem(itemId2).getQuantity()).isEqualTo(10);

    }



    private static CategoryDto.CategoryRequestDto getCategoryRequestDto(String name) {
        CategoryDto.CategoryRequestDto categoryRequestDto = new CategoryDto.CategoryRequestDto(name);
        return categoryRequestDto;
    }

    private static UserJoinRequestDto getUserJoinRequestDto() {
        Address address = getAddress();
        UserJoinRequestDto dto = new UserJoinRequestDto("Spring", "java@gmail.com", "12345", address);
        return dto;
    }

    private static Address getAddress() {
        Address address = Address.createAddress("home", "seoul", "gangnam", "54321");
        return address;
    }

}