package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Category;
import project.commercePJT.repository.CategoryRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 생성
    @Transactional
    public Long createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return savedCategory.getId();
    }

    // 카테고리 조회
    public Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                ()-> new IllegalStateException("해당 카테고리가 존재하지 않습니다 Id="+categoryId));
    }
}
