package project.commercePJT.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.commercePJT.dto.CartDto;
import project.commercePJT.service.CartService;

import static project.commercePJT.dto.CartDto.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 담기 버튼
    @PostMapping("/add")
    public String addCart(CartItemRequestDto dto){
        Long mockUserId = 1L;
        cartService.addCart(mockUserId, dto);
        return "redirect:/items";
    }

    // 장바구니 목록 조회
    @GetMapping
    public String cartList(Model model) {
        Long mockUserId = 1L;
        CartResponseDto cartResponseDto = cartService.findCartByUserId(mockUserId);
        model.addAttribute("cart", cartResponseDto);
        return "cart/list";
    }

}
