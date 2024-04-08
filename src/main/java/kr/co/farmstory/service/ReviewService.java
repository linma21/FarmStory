package kr.co.farmstory.service;

import com.querydsl.core.Tuple;
import kr.co.farmstory.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 목록 조회
    public List<Tuple> selectReviews(int prodno){
        return reviewRepository.selectReviewsAndNick(prodno);
    }
}
