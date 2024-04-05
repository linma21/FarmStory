package kr.co.farmstory.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.farmstory.dto.*;
import kr.co.farmstory.entity.*;
import kr.co.farmstory.repository.MarketRepository;
import kr.co.farmstory.repository.OrderRepository;
import kr.co.farmstory.repository.custom.MarketRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketService {

    private OrderRepository orderRepository;
    private final MarketRepository marketRepository;
    private final ModelMapper modelMapper;


    // 장보기 글목록 페이지 - 장보기 목록 출력
    public MarketPageResponseDTO selectProducts(MarketPageRequestDTO marketPageRequestDTO){
        log.info("selectProducts Service 1");
        Pageable pageable = marketPageRequestDTO.getPageable("no");

        log.info("selectProducts Service 2 pageable : " + pageable.toString());
        log.info("selectProducts Service 2 pageable : " + marketPageRequestDTO.toString());

        // select * from `product` order by no desc limt (0, 10) + 사진
        Page<Product> productList = marketRepository.selectProducts(marketPageRequestDTO, pageable);

        log.info("productList : " + productList.toString());

        List<ProductDTO> productDTO = productList.getContent().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        log.info("productDTO : " + productDTO.toString());

        int total = (int) productList.getTotalElements();

        return MarketPageResponseDTO.builder()
                .pageRequestDTO(marketPageRequestDTO)
                .dtoList(productDTO)
                .total(total)
                .build();
    }
    // 장보기 글보기 페이지 - 장보기 게시글 출력
    public ProductDTO selectProduct(int prodeno){
        List<Tuple> joinProduct = marketRepository.selectProduct(prodeno);
        // List에서 Product, Images 엔티티 꺼낸 후 ProductDTO로 병합
        ProductDTO joinProductDTO = joinProduct.stream()
                .map(tuple ->
                        {
                            Product product = tuple.get(0, Product.class);
                            Images images = tuple.get(1, Images.class);
                            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                            ImagesDTO imagesDTO = modelMapper.map(images, ImagesDTO.class);
                            productDTO.setTitleImg(imagesDTO.getThumb240());
                            productDTO.setContentImg(imagesDTO.getThumb750());
                            return productDTO;
                        }
                    )
                .findFirst()
                .orElse(null);
    return joinProductDTO;
    }

    // 주문 목록 조회
    public List<OrderDetailProductDTO> getOrderDetailsWithProductByUserId(String userId) {
        List<Tuple> results = marketRepository.findOrderDetailsWithProductNameByUserId(userId);

        List<OrderDetailProductDTO> orderDetailProductDTOList =  results.stream().map(tuple -> {
            // Here, directly use the generated Q classes to access tuple elements in a type-safe manner
            Integer detailNo = tuple.get(QOrderDetail.orderDetail.detailno);
            Integer orderNo = tuple.get(QOrderDetail.orderDetail.orderNo);
            Integer prodNo = tuple.get(QOrderDetail.orderDetail.prodno);
            Integer count = tuple.get(QOrderDetail.orderDetail.count);
            String prodName = tuple.get(QProduct.product.prodname);

            // Creating and returning the DTO
            OrderDetailProductDTO dto = new OrderDetailProductDTO();
            dto.setDetailNo(detailNo);
            dto.setOrderNo(orderNo);
            dto.setProdNo(prodNo);
            dto.setCount(count);
            dto.setProdName(prodName);

            return dto;
        }).collect(Collectors.toList());
        log.info("orderDetailProductDTOList : " + orderDetailProductDTOList.toString());
        return orderDetailProductDTOList;
    }
}
