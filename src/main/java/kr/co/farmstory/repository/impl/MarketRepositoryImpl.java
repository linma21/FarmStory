package kr.co.farmstory.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.entity.QProduct;
import kr.co.farmstory.repository.custom.MarketRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MarketRepositoryImpl implements MarketRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private QProduct qProduct = QProduct.product;

    @Override
    public Page<Product> selectProducts(MarketPageRequestDTO marketPageRequestDTO, Pageable pageable) {

        // select * from `product` order by no desc limt (0, 10)
        List<Product> productList = jpaQueryFactory
                                    .selectFrom(qProduct)
                                    .offset(pageable.getOffset())
                                    .limit(pageable.getPageSize())
                                    .orderBy(qProduct.prodno.desc())
                                    .fetch();

        long total = jpaQueryFactory
                .selectFrom(qProduct)
                .fetchCount();

        return new PageImpl<>(productList, pageable, total);
    }

}
