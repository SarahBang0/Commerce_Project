package project.commercePJT.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.Address;

import static org.assertj.core.api.Assertions.*;
import static project.commercePJT.dto.CartDto.*;
import static project.commercePJT.dto.CategoryDto.*;
import static project.commercePJT.dto.ItemDto.*;
import static project.commercePJT.dto.UserDto.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired UserService userService;
    @Autowired CategoryService categoryService;
    @Autowired ItemService itemService;
    @Autowired CartService cartService;

    @Test
    void 장바구니_담기_성공() {
        //given
        // 1. 회원 생성
        Long userId = userService.joinUser(getUserJoinRequestDto());
        // 2. 카테고리 생성
        Long categoryId = categoryService.createCategory(getCategoryRequestDto("clothes"));
        CategoryResponseDto category = categoryService.findCategory(categoryId);
        // 3. 상품 생성
        Long itemId = itemService.saveItem(getItemRequestDto(categoryId));

        //when
        CartItemRequestDto cartItemRequestDto = new CartItemRequestDto(itemId, 3);
        Long cartItemId = cartService.addCart(userId, cartItemRequestDto);
        CartItemRequestDto cartItemRequestDto2 = new CartItemRequestDto(itemId, 2);
        cartService.addCart(userId,cartItemRequestDto2);

        //then
        CartResponseDto cartResponseDto = cartService.findCartByUserId(userId);
        assertThat(cartResponseDto.getUserName()).isEqualTo("Spring");
        assertThat(cartResponseDto.getCartItems().size()).isEqualTo(1);
        assertThat(cartResponseDto.getTotalPrice()).isEqualTo(50000L);
    }

    private static ItemRequestDto getItemRequestDto(Long categoryId) {
        ItemRequestDto dto = new ItemRequestDto("t-shirts", 10, 10000L, categoryId);
        return dto;
    }

    private static CategoryRequestDto getCategoryRequestDto(String name) {
        CategoryRequestDto dto = new CategoryRequestDto(name);
        return dto;
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