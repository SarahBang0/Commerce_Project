package project.commercePJT.ApiController;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.commercePJT.domain.item.Category;
import project.commercePJT.dto.CategoryDto;
import project.commercePJT.service.CategoryService;

import java.util.List;

import static project.commercePJT.dto.CategoryDto.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryApiController {

    @Autowired private final CategoryService categoryService;

    // 카테고리 등록
    @PostMapping
    public Long createCategory(@RequestBody CategoryRequestDto dto) {
        return categoryService.createCategory(dto);
    }

    @GetMapping
    public List<CategoryResponseDto> categories() {
        return categoryService.findCategories();
    }

}
