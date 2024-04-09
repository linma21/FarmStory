package kr.co.farmstory.service;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.*;
import kr.co.farmstory.dto.ImagesDTO;
import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.MarketPageResponseDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.entity.Cart_product;
import kr.co.farmstory.entity.Images;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final ModelMapper modelMapper;


    // 장보기 글목록 페이지 - 장보기 목록 출력
    public MarketPageResponseDTO selectProducts(MarketPageRequestDTO marketPageRequestDTO){
        log.info("selectProducts Service 1");
        Pageable pageable = marketPageRequestDTO.getPageable("no");

        log.info("selectProducts Service 2 pageable : " + pageable.toString());
        log.info("selectProducts Service 2 pageable : " + marketPageRequestDTO.toString());

        // select * from `product` order by no desc limit (0, 10) + 사진
        Page<Tuple> productList = marketRepository.selectProducts(marketPageRequestDTO, pageable);

        log.info("productList : " + productList.toString());

            List<ProductDTO> productDTOs = productList.getContent().stream()
                    .map(tuple -> {
                                Product product = tuple.get(0, Product.class);
                                String thumb240 = tuple.get(1, String.class);

                                ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                                productDTO.setTitleImg(thumb240);
                                return productDTO;
                            }
                    )
                    .toList();

        log.info("productDTO : " + productDTOs.toString());

        int total = (int) productList.getTotalElements();

        return MarketPageResponseDTO.builder()
                .pageRequestDTO(marketPageRequestDTO)
                .dtoList(productDTOs)
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

        List<OrderDetailProductDTO> orderDetailProductDTOList = results.stream().map(tuple -> {
            // Here, directly use the generated Q classes to access tuple elements in a type-safe manner
            Integer detailNo = tuple.get(0, Integer.class);
            Integer orderNo = tuple.get(1, Integer.class);
            Integer prodNo = tuple.get(2, Integer.class);
            Integer count = tuple.get(3, Integer.class);
            String prodName = tuple.get(4, String.class);

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

    // 장바구니 목록
    public List<ProductDTO> selectCartForMarket(String uid){
        log.info("marketCartService1");
        List<Tuple> qProductList = marketRepository.selectCartForMarket(uid);
        log.info("marketCartService2-qProductList : " + qProductList.toString());
        // 참조 List
        List<ProductDTO> productDTOs = qProductList.stream()
                .map(tuple ->
                    {
                        Cart_product cart_product = tuple.get(0, Cart_product.class);
                        Product product = tuple.get(1, Product.class);
                        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                        productDTO.setCount(cart_product.getCount());
                        productDTO.setCart_prodNo(cart_product.getCart_prodNo());
                        return productDTO;
                    }
                )
            .toList();
        log.info("marketCartService3-productDTOs : " + productDTOs.toString());
        return productDTOs;
    }

    // 장바구니 count 수정
    public ResponseEntity<?> modifyCount(int[] cart_prodNos, int[] counts){
        boolean result = marketRepository.modifyCount(cart_prodNos, counts);
        Map<String, String> response = new HashMap<>();
        if (result){
            response.put("data","수량 변경 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            response.put("data","수량 변경 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 장바구니에서 선택 상품 삭제
    public ResponseEntity<?> deleteCart(int[] cart_prodNos){
        boolean result = marketRepository.deleteCart(cart_prodNos);
        Map<String, String> response = new HashMap<>();
        if (result){
            response.put("data","삭제 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            response.put("data","삭제 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
