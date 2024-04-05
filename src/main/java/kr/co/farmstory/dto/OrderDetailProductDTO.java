package kr.co.farmstory.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderDetailProductDTO {
    private int detailNo;
    private int prodNo;
    private int orderNo;
    private int count;
    private String prodName;
}

