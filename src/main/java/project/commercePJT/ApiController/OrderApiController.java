package project.commercePJT.ApiController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.commercePJT.dto.UserDto;
import project.commercePJT.service.OrderService;
import project.commercePJT.service.UserService;

import java.util.List;

import static project.commercePJT.dto.OrderDto.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public Long createOrder(@RequestBody OrderRequestDto dto) {
       return orderService.createOrder(dto);
    }

    // 주문 조회
    @GetMapping("/{orderId}")
    public OrderResponseDto order(@PathVariable Long orderId) {
        return orderService.findOrder(orderId);
    }

    // 회원별 주문 조회
    @GetMapping("/users/{userId}")
    public List<OrderResponseDto> orderByUser(@PathVariable Long userId){
        return orderService.findOrdersByUserId(userId);
    }

    // 주문 전체 조회
    @GetMapping
    public List<OrderResponseDto> orders(){
        return orderService.findOrders();
    }

    // 주문 취소
    @DeleteMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

}
