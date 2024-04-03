package kr.co.farmstory.service;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.ImagesDTO;
import kr.co.farmstory.dto.ProductDTO;
import kr.co.farmstory.entity.Images;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.repository.ImagesRepository;
import kr.co.farmstory.repository.MarketRepository;
import kr.co.farmstory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

    private final MarketRepository marketRepository;
    private final ImagesRepository imagesRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Value("${file.prodImg.path}")
    private String fileUploadPath;

    // 상품 등록
    public void productRegister(ProductDTO productDTO, MultipartFile thumb120, MultipartFile thumb240, MultipartFile thumb750) {

        log.info("파일 업로드 service1 productDTO : " + productDTO.toString());
        log.info("파일 업로드 service2 thumb120 : " + thumb120);
        log.info("파일 업로드 service3 thumb240 : " + thumb240);
        log.info("파일 업로드 service4 thumb750 : " + thumb750);

        // 상품 정보 등록 (정보 저장 & thumb120 저장)
        File file = new File(fileUploadPath);
        if (!file.exists()) {
            file.mkdir();
        }

        String path = file.getAbsolutePath();

        // 저장
        Product savedProduct = new Product();

        if (!thumb120.isEmpty()) {
            // oName, sName 구하기
            String oName = thumb120.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String sName = UUID.randomUUID().toString() + ext;
            log.info("파일 업로드 service5 oName : " + oName);
            log.info("파일 업로드 service6 sName : " + sName);

            try {
                // 파일 저장
                thumb120.transferTo(new File(path, sName));
                // 파일 이름 DTO에 저장
                productDTO.setThumb(sName);

                // 상품 정보 DB 저장
                Product product = modelMapper.map(productDTO, Product.class);
                log.info("파일 업로드 service7 product : " + product.toString());
                savedProduct = marketRepository.save(product);
                log.info("파일 업로드 service7 savedProduct : " + savedProduct.toString());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        // 상품 이미지 등록
        Images savedImage = new Images();
        if (!thumb240.isEmpty() && !thumb750.isEmpty()) {
            // oName, sName 구하기
            String thumb240oName = thumb240.getOriginalFilename();
            String thumb240ext = thumb240oName.substring(thumb240oName.lastIndexOf("."));
            String thumb240sName = UUID.randomUUID().toString() + thumb240ext;

            String thumb750oName = thumb750.getOriginalFilename();
            String thumb750ext = thumb750oName.substring(thumb750oName.lastIndexOf("."));
            String thumb750sName = UUID.randomUUID().toString() + thumb750ext;


            log.info("파일 업로드 service8 thumb240sName : " + thumb240sName);
            log.info("파일 업로드 service9 thumb750sName : " + thumb750sName);

            try {
                // 이미지 저장
                thumb240.transferTo(new File(path, thumb240sName));
                thumb750.transferTo(new File(path, thumb750sName));
                // 이미지 이름 DTO에 저장
                ImagesDTO imagesDTO = ImagesDTO.builder()
                        .prodno(savedProduct.getProdno())
                        .thumb240(thumb240sName)
                        .thumb750(thumb750sName)
                        .build();

                // 이미지 정보 DB 저장
                Images image = modelMapper.map(imagesDTO, Images.class);
                log.info("파일 업로드 service10 image : " + image.toString());
                savedImage = imagesRepository.save(image);
                log.info("파일 업로드 service10 savedImage : " + savedImage.toString());


            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        log.info("파일 업로드 service11 끝");

    }


    /*
    public List<ProductDTO> getAllProductDTOs(Product product) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

     */

    
    //제품 목록을 조회
    public List<ProductDTO> products(){
        List<Product> products = productRepository.findAll();

        log.info("AdminService - products : "+products.toString());

        return products.stream().map(product -> modelMapper.map(product,ProductDTO.class))
                .collect(Collectors.toList());
    }
}

