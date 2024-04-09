package kr.co.farmstory.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReviewDTO {
    private int rno;
    private String uid;
    private int prodno;
    private int score;
    private String thumbnail;
}
