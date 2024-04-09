package kr.co.farmstory.controller;

import kr.co.farmstory.dto.ReviewDTO;
import kr.co.farmstory.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    // review insert - Form 데이터 받아서 처리
    @PostMapping("/review")
    public String reviewWrite(@RequestParam("thumb") MultipartFile thumb, ReviewDTO reviewDTO){
        log.info("reviewWrite Cont 1 : " + reviewDTO.getUid());
        log.info("reviewWrite Cont 2 : " + reviewDTO.getScore());
        log.info("reviewWrite Cont 3 : " + thumb);

        // 리뷰 등록 service
        reviewService.insertReview(reviewDTO, thumb);
        return "redirect:/market/orderList?uid="+reviewDTO.getUid();
    }
}
