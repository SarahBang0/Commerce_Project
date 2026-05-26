package project.commercePJT.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.commercePJT.dto.ItemDto;
import project.commercePJT.dto.OrderDto;
import project.commercePJT.service.ItemService;
import project.commercePJT.service.OrderService;

import java.util.List;

import static project.commercePJT.dto.OrderDto.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired private final OrderService orderService;
    @Autowired private final ItemService itemService;

    // 주문 목록 조회
    @GetMapping
    public String orders(Model model) {
        List<OrderResponseDto> orders = orderService.findOrders();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public String order(@PathVariable Long orderId, Model model) {
        OrderResponseDto order = orderService.findOrder(orderId);
        model.addAttribute("order", order);
        return "orders/detail";
    }

    // 주문 생성 폼 이동
    @GetMapping("/create")
    public String orderCreateForm(Model model) {
        List<ItemDto.ItemResponseDto> items = itemService.findItems();
        model.addAttribute("items", items);
        return "orders/create";
    }

    // 주문 생성 폼
    @PostMapping("/create")
    public String orderCreate(@RequestBody OrderRequestDto dto) {
        orderService.createOrder(dto);
        return "redirect:/orders";
    }
}
