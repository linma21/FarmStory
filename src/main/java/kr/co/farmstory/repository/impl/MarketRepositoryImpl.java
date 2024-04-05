package kr.co.farmstory.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.entity.*;
import kr.co.farmstory.repository.custom.MarketRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class MarketRepositoryImpl implements MarketRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct qProduct = QProduct.product;
    private final QImages qImages = QImages.images;
    private final QOrders orders = QOrders.orders;
    private final QOrderDetail orderDetail = QOrderDetail.orderDetail;
    private final QProduct product = QProduct.product;

    // 장보기 게시판 목록 출력 (market/list)
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

    // 장보기 게시판 게시글 출력 (market/view)
    public List<Tuple> selectProduct(int prodno){
        // select * from `product` as a join `images` as b on a.prodno = b.prodno where a`prodno` = ?
        List<Tuple> joinProduct = jpaQueryFactory
                                        .select(qProduct, qImages)
                                        .from(qProduct)
                                        .join(qImages)
                                        .on(qProduct.prodno.eq(qImages.prodno))
                                        .where(qProduct.prodno.eq(prodno))
                                        .fetch();

        log.info("results : " + joinProduct);
        return joinProduct;

    };


    @Override
    public List<Tuple> findOrderDetailsWithProductNameByUserId(String userId) {
        QueryResults<Tuple> results = jpaQueryFactory
                .select(orderDetail.detailno, orderDetail.count, product.prodname, product.prodno, orders.orderNo)
                .from(orderDetail)
                .join(orders).on(orderDetail.orderNo.eq(orders.orderNo))
                .join(product).on(orderDetail.prodno.eq(product.prodno))
                .where(orders.uid.eq(userId))
                .fetchResults();
        log.info("results! : " + results.toString());
        log.info("results!!!!! : " + userId);

        return results.getResults();
    }
}
