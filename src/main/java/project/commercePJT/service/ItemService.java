package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Item;
import project.commercePJT.dto.ItemDto;
import project.commercePJT.repository.CategoryRepository;
import project.commercePJT.repository.ItemRepository;
import project.commercePJT.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    // 상품 등록
    @Transactional
    public Long saveItem(ItemDto dto) {
        Item item = Item.createItem(dto.getName(), dto.getQuantity(), dto.getPrice(), dto.getCategory());
        Item savedItem = itemRepository.save(item);
        return savedItem.getId();
    }

    // 상품 수정
    @Transactional
    public void updateItem(Long itemId, ItemDto dto) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalStateException("해당 상품이 존재하지 않습니다. Id=" + itemId)
        );
        item.changeItem(dto.getName(), dto.getQuantity(), dto.getPrice(), dto.getCategory());
    }

    // 상품 삭제
    @Transactional
    public void removeItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalStateException("해당 상품이 존재하지 않습니다. Id=" + itemId)
        );
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
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new IllegalStateException("해당 상품이 존재하지 않습니다. Id=" + itemId)
        );
        return new ItemDto(item);
    }

}
