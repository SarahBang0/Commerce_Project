package project.commercePJT.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.commercePJT.dto.CategoryDto;
import project.commercePJT.service.CategoryService;

import java.util.List;

import static project.commercePJT.dto.CategoryDto.*;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired private final CategoryService categoryService;


    // 카테고리 생성
    @PostMapping("/create")
    public String categoryCreate(CategoryRequestDto dto) {
        categoryService.createCategory(dto);
        return "redirect:/categories";
    }

    // 카테고리 관리 페이지 (목록 조회 & 생성 폼)
    @GetMapping
    public String categories(Model model) {
        List<CategoryResponseDto> categories = categoryService.findCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    // 카테고리 수정 폼 이동
    @GetMapping("/edit/{categoryId}")
    public String categoryEditForm(@PathVariable Long categoryId, Model model) {
        CategoryResponseDto category = categoryService.findCategory(categoryId);
        model.addAttribute("category", category);
        return "categories/edit";
    }

    // 카테고리 수정 폼
    @PostMapping("/edit/{categoryId}")
    public String categoryEdit(@PathVariable Long categoryId, CategoryRequestDto dto) {
        categoryService.updateCategory(categoryId, dto);
        return "categories/list";
    }
}
