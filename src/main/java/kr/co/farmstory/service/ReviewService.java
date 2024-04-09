package kr.co.farmstory.service;

import com.querydsl.core.Tuple;
import kr.co.farmstory.dto.ReviewDTO;
import kr.co.farmstory.dto.ReviewPageRequestDTO;
import kr.co.farmstory.dto.ReviewPageResponseDTO;
import kr.co.farmstory.entity.Product;
import kr.co.farmstory.entity.Review;
import kr.co.farmstory.repository.ProductRepository;
import kr.co.farmstory.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Value("${file.prodImg.path}")
    private String fileUploadPath;

    // 리뷰 목록 조회
    public ReviewPageResponseDTO selectReviews(int prodno){

        ReviewPageRequestDTO pageRequestDTO = new ReviewPageRequestDTO();
        Pageable pageable = pageRequestDTO.getPageable("no");
        log.info("selectReviews Serv ...1 ");
        // 리뷰 목록  Page 조회
        Page<Tuple> results = reviewRepository.selectReviewsAndNick(prodno, pageRequestDTO, pageable);

        // Page<Tuple>을 List<ReviewDTO>로 변환
        List<ReviewDTO> reviewList = results.getContent().stream()
                .map(tuple -> {
                    // Tuple 에서 Entity GET
                    Review review = tuple.get(0, Review.class);
                    String nick = tuple.get(1, String.class);

                    // Entity -> DTO
                    ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
                    reviewDTO.setNick(nick);
                    log.info("selectReviews Serv ...3 : " + reviewDTO.toString());
                    return reviewDTO;
                }).toList();
        log.info("selectReviews Serv ...4 : " + reviewList.toString());

        // List<ReviewDTO>와 Page 리턴
        int total = (int) results.getTotalElements();
        return ReviewPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(reviewList)
                .total(total)
                .build();
    }
    // 리뷰 작성 + 상품 테이블 recount ++
    @Transient
    public String insertReview(ReviewDTO reviewDTO, MultipartFile thumb){

        log.info("리뷰 업로드 insertReview1 reviewDTO : " + reviewDTO.toString());
        log.info("리뷰 업로드 insertReview2 이미지 : " + thumb);

        // thumbnail 저장
        java.io.File file = new java.io.File(fileUploadPath);
        if(!file.exists()){
            file.mkdir();
        }
        String path = file.getAbsolutePath();

        if (!thumb.isEmpty()) {
            // oName, sName 구하기
            String oName = thumb.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String sName = UUID.randomUUID().toString() + ext;
            log.info("insertReview oName : " + oName);
            log.info("insertReview sName : " + sName);

            try {
                // 파일 저장
                thumb.transferTo(new File(path, sName));
                // thumb 이름 DTO에 입력
                reviewDTO.setThumbnail(sName);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        // DTO -> Entity
        Review review = modelMapper.map(reviewDTO, Review.class);

        // review insert
        reviewRepository.save(review);

        // product recount ++
        Product product = productRepository.findById(review.getProdno()).get();
        product.setRecount(product.getRecount() + 1);

        // product update
        productRepository.save(product);
        return null;
    }
}
