package project.commercePJT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.commercePJT.domain.item.Category;
import project.commercePJT.domain.item.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByName(String name);
    List<Item> findByCategoryId(Long categoryId);
}
