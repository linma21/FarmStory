package kr.co.farmstory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewRatioDTO {
    private int count;
    private int sum;
    private long avg;
    private int percent1;
    private int percent2;
    private int percent3;
    private int percent4;
    private int percent5;

    @Builder
    public ReviewRatioDTO(int count, int sum, long avg){
        this.count = count;
        this.sum = sum;
        this.avg = avg;

    }
}
