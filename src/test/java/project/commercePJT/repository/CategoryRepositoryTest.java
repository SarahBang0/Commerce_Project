package project.commercePJT.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Category;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired CategoryRepository categoryRepository;

    @Test
    void 카테고리_생성_성공() {
        //given
        Category clothes = Category.createCategory("clothes");

        //when
        categoryRepository.save(clothes);

        //then
        Category findCategory = categoryRepository.findByName(clothes.getName()).get(0);
        assertThat(findCategory).isEqualTo(clothes);
    }
}