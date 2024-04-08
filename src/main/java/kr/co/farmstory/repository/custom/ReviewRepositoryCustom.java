package kr.co.farmstory.repository.custom;


import com.querydsl.core.Tuple;

import java.util.List;

public interface ReviewRepositoryCustom {

    public List<Tuple> selectReviewsAndNick(int prodno);
}
