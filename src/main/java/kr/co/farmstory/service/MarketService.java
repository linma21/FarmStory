package kr.co.farmstory.service;

import kr.co.farmstory.dto.MarketPageRequestDTO;
import kr.co.farmstory.dto.MarketPageResponseDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final ModelMapper modelMapper;

    // select * from `product` order by no desc limt (0, 10)
    public MarketPageResponseDTO selectProducts(MarketPageRequestDTO marketPageRequestDTO){
        log.info("selectProducts Service 1");
        Pageable pageable = marketPageRequestDTO.getPageable("no");

        log.info("selectProducts Service 2");
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
}
