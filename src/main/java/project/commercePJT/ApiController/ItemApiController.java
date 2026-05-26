package project.commercePJT.ApiController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.commercePJT.dto.ItemDto;
import project.commercePJT.service.ItemService;

import java.util.List;

import static project.commercePJT.dto.ItemDto.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    // 상품 목록 조회
    @GetMapping
    public List<ItemResponseDto> items() {
        return itemService.findItems();
    }

    // 상품 상세 조회
    @GetMapping("/{itemId}")
    public ItemResponseDto item(@PathVariable Long itemId) {
        return itemService.findItem(itemId);
    }

    // 카테고리별 상품 조회
    @GetMapping("/category/{categoryId}")
    public List<ItemResponseDto> itemsByCategory(@PathVariable Long categoryId) {
        return itemService.findItemsByCategory(categoryId);
    }

    // 상품 등록
    @PostMapping
    public Long saveItem(@RequestBody ItemRequestDto dto) {
        return itemService.saveItem(dto);
    }

    // 상품 정보 수정
    @PostMapping("/{itemId}")
    public ItemResponseDto updateItem(@RequestBody ItemRequestDto dto, @PathVariable Long itemId) {
        itemService.updateItem(itemId, dto);
        return itemService.findItem(itemId);
    }

    // 상품 삭제
    @DeleteMapping("/{itemId}/remove")
    public void removeItem(@PathVariable Long itemId) {
        itemService.removeItem(itemId);
    }
}
