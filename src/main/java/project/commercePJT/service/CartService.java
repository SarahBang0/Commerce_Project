package project.commercePJT.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.commercePJT.domain.item.Item;
import project.commercePJT.repository.CartRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    @Autowired private final CartRepository cartRepository;

    // 장바구니에 상품 담기
/*    @Transactional
    public Long addCart(Item item, Long userId) {

    }*/
}
