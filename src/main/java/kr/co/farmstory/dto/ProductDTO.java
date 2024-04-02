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
    private String thumbnail;
    private String cate;
    private int price;
    private int stock;
    private int delCost;
    private int delType;
    private int discount;
    private String etc;
}

