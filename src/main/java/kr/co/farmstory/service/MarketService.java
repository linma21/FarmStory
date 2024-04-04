package kr.co.farmstory.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.ImagesDTO;
import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.MarketPageResponseDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.entity.Cart_product;
import kr.co.farmstory.entity.Images;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.entity.QProduct;
import kr.co.farmstory.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    // 장바구니 목록
    public List<ProductDTO> selectCartForMarket(String uid){
        log.info("marketCartService1");
        List<Tuple> qProductList = marketRepository.selectCartForMarket(uid);
        log.info("marketCartService2-qProductList : " + qProductList.toString());
        // 참조 List
        List<ProductDTO> productDTOs = qProductList.stream()
                .map(tuple ->
                    {
                        Integer cart_product = tuple.get(0, Integer.class);
                        Product product = tuple.get(1, Product.class);
                        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                        productDTO.setCount(cart_product);
                        return productDTO;
                    }
                )
            .toList();
        log.info("marketCartService3-productDTOs : " + productDTOs.toString());
        return productDTOs;
    }
}
