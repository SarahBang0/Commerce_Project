package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Category;
import project.commercePJT.domain.item.Item;
import project.commercePJT.dto.ItemDto;
import project.commercePJT.repository.CategoryRepository;
import project.commercePJT.repository.ItemRepository;
import project.commercePJT.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    // 상품 등록
    @Transactional
    public Long saveItem(ItemDto dto) {
        Category category = getCategory(dto);
        Item item = Item.createItem(dto.getName(), dto.getQuantity(), dto.getPrice(), category);
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    // 상품 수정
    @Transactional
    public void updateItem(Long itemId, ItemDto dto) {
        Item item = getItem(itemId);
        Category category = getCategory(dto);
        item.changeItem(dto.getName(), dto.getQuantity(), dto.getPrice(), category);
    }

    // 상품 삭제
    @Transactional
    public void removeItem(Long itemId) {
        Item item = getItem(itemId);
        itemRepository.delete(item);
    }

    // 상품 목록 조회
    public List<ItemDto> findItems() {
        return itemRepository.findAll().stream()
                .map(ItemDto::new)
                .toList();
    }

    //상품 상세 조회
    public ItemDto findItem(Long itemId) {
        Item item = getItem(itemId);
        return new ItemDto(item);
    }


    private Category getCategory(ItemDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                ()-> new IllegalStateException("해당 카테고리가 존재하지 않습니다. Id=" + dto.getCategoryId())
        );
        return category;
    }

    private Item getItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalStateException("해당 상품이 존재하지 않습니다. Id=" + itemId)
        );
        return item;
    }


}
