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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MarketRepositoryImpl implements MarketRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct qProduct = QProduct.product;

    @Override
    public Page<Product> selectProducts(MarketPageRequestDTO marketPageRequestDTO, Pageable pageable) {

        /*
            3가지로 분기 // 중복 코드 합치기
            1. 파라미터 하나도 없을 때
            2. cate만 있을 때
            3. type keyword 있을 때
        */
        List<Product> productList = new ArrayList<>();
        long total = 0;
        if ((marketPageRequestDTO.getCate()==null || marketPageRequestDTO.getCate().isEmpty()) && marketPageRequestDTO.getType()==null){
            // 1. 파라미터 없이 호출했을 경우 (상단 배너로 호출)
            // select * from `product` order by no desc limt (0, 10)
            productList = jpaQueryFactory
                    .selectFrom(qProduct)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(qProduct.prodno.desc())
                    .fetch();

            total = jpaQueryFactory.selectFrom(qProduct).fetchCount();
        }else if (marketPageRequestDTO.getCate()!=null && marketPageRequestDTO.getType()==null){
            // 2. cate만 있는 경우 (상품 분류로 호출)
            // select * from `product` where `cate`=? order by no desc limt (0, 10)
            productList = jpaQueryFactory
                    .selectFrom(qProduct)
                    .where(qProduct.cate.eq(marketPageRequestDTO.getCate()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(qProduct.prodno.desc())
                    .fetch();

            total = jpaQueryFactory.selectFrom(qProduct).where(qProduct.cate.eq(marketPageRequestDTO.getCate())).fetchCount();
        }else {
            // 3. type keyword 있는 경우 (검색으로 호출)
            // select * from `product` where `type`= keyword order by no desc limt (0, 10)
        }

        return new PageImpl<>(productList, pageable, total);
    }

}
