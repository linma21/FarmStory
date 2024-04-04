package kr.co.farmstory.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.OrderListResponseDTO;
import kr.co.farmstory.dto.PageRequestDTO;
import kr.co.farmstory.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MarketRepositoryCustom {
    // market/list 페이지 products 조회
    public Page<Product> selectProducts(MarketPageRequestDTO marketPageRequestDTO, Pageable pageable);
    // market/view 페이지 product 조회
    public List<Tuple> selectProduct(int prodno);
    // admin/order/list 페이지 조회
    public Page<Tuple> orderList(PageRequestDTO pageRequestDTO, Pageable pageable);
    // market/cart 페이지 cart_product 조회
    public List<Tuple> selectCartForMarket(String uid);
}
