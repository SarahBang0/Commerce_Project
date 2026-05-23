package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Category;
import project.commercePJT.dto.CategoryDto;
import project.commercePJT.exception.ErrorCode;
import project.commercePJT.exception.ResourceNotFoundException;
import project.commercePJT.repository.CategoryRepository;

import java.util.List;

import static project.commercePJT.dto.CategoryDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 생성
    @Transactional
    public Long createCategory(CategoryRequestDto dto) {
        Category category = Category.createCategory(dto.getName());
        return categoryRepository.save(category).getId();
    }

    // 카테고리 조회
    public CategoryResponseDto findCategory(Long categoryId) {
        Category category = validateCategoryExists(categoryId);
        return new CategoryResponseDto(category);
    }

    // 카테고리 목록 조회
    public List<CategoryResponseDto> findCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::new)
                .toList();
    }

    private Category validateCategoryExists(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(categoryId, ErrorCode.CATEGORY_NOT_FOUND));
        return category;
    }

}
