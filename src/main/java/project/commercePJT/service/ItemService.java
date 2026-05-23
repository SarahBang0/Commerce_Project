package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Category;
import project.commercePJT.domain.item.Item;
import project.commercePJT.dto.ItemDto;
import project.commercePJT.exception.ErrorCode;
import project.commercePJT.exception.ResourceNotFoundException;
import project.commercePJT.repository.CategoryRepository;
import project.commercePJT.repository.ItemRepository;

import java.util.List;

import static project.commercePJT.dto.ItemDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    // 상품 등록
    @Transactional
    public Long saveItem(ItemRequestDto dto) {
        Category category = validateCategoryExists(dto.getCategoryId());
        Item item = Item.createItem(dto.getName(), dto.getQuantity(), dto.getPrice(), category);
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    // 상품 수정
    @Transactional
    public void updateItem(Long itemId, ItemRequestDto dto) {
        Item item = validateItemExists(itemId);
        Category category = validateCategoryExists(dto.getCategoryId());
        item.changeItem(dto.getName(), dto.getQuantity(), dto.getPrice(), category);
    }

    // 상품 삭제
    @Transactional
    public void removeItem(Long itemId) {
        Item item = validateItemExists(itemId);
        itemRepository.delete(item);
    }

    // 상품 목록 조회
    public List<ItemResponseDto> findItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponseDto::new)
                .toList();
    }

    //상품 상세 조회
    public ItemResponseDto findItem(Long itemId) {
        Item item = validateItemExists(itemId);
        return new ItemResponseDto(item);
    }

    // 카테고리별 상품 조회
    public List<ItemResponseDto> findItemsByCategory(Long categoryId) {
        Category category = validateCategoryExists(categoryId);
        return itemRepository.findByCategoryId(categoryId).stream()
                .map(ItemResponseDto::new)
                .toList();
    }


    private Category validateCategoryExists(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException(categoryId, ErrorCode.CATEGORY_NOT_FOUND)
        );
        return category;
    }

    private Item validateItemExists(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException(itemId, ErrorCode.ITEM_NOT_FOUND)
        );
        return item;
    }


}
