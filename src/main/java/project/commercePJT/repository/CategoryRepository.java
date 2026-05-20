package project.commercePJT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.commercePJT.domain.item.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByName(String name);
}
