package kr.co.farmstory.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {
    private int prodno;
    private String prodname;
    private int amount;
    private String thumb;
    private String cate;
    private int price;
    private int stock;
    private int delCost;
    private int delType;
    private int discount;
    private String etc;
    private LocalDateTime rdate;
    // 상품 사진 출력을 위한 추가 필드
    private String titleImg;
    private String contentImg;
    private int cart_prodNo;
    private int count;
}

