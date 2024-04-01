package kr.co.farmstory.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {
    private int prodno;
    private String prodname;
    private String type;
    private int stock;
    private int price;
    private int discount;
    private String company;

}

