package project.commercePJT.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.commercePJT.dto.CategoryDto;
import project.commercePJT.dto.ItemDto;
import project.commercePJT.service.CategoryService;
import project.commercePJT.service.ItemService;

import java.util.List;

import static project.commercePJT.dto.CategoryDto.*;
import static project.commercePJT.dto.ItemDto.*;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    @Autowired
    private final ItemService itemService;
    @Autowired
    private final CategoryService categoryService;

    // 상품 등록 폼 이동
    @GetMapping("/create")
    public String ItemCreateForm(Model model) {
        List<CategoryResponseDto> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);
        return "items/create";
    }

    // 상품 등록 폼
    @PostMapping("/create")
    public String ItemCreate(@Valid ItemRequestDto dto) {
        Long itemId = itemService.saveItem(dto);
        return "redirect:/items";
    }

    // 상품 목록 조회
    @GetMapping
    public String Items(Model model) {
        List<ItemResponseDto> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/list";
    }

    // 상품 상세 조회
    @GetMapping("/{itemId}")
    public String Item(@PathVariable Long itemId, Model model) {
        ItemResponseDto item = itemService.findItem(itemId);
        model.addAttribute("item", item);
        return "items/detail";
    }


    // 상품 수정 폼 이동
    @GetMapping("/{itemId}/edit")
    public String ItemEditForm(@PathVariable Long itemId, Model model) {
        ItemResponseDto item = itemService.findItem(itemId);
        List<CategoryResponseDto> categories = categoryService.findCategories();
        model.addAttribute("item", item);
        model.addAttribute("categories",categories);
        return "items/edit";
    }

    // 상품 수정 폼
    @PostMapping("/{itemId}/edit")
    public String ItemEdit(@PathVariable Long itemId,ItemRequestDto dto) {
        List<CategoryResponseDto> categories = categoryService.findCategories();
        itemService.updateItem(itemId, dto);
        return "redirect:/items";
    }

    // 카테고리별 상품 조회

    // 상품 삭제
}
