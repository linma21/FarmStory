package kr.co.farmstory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewRatioDTO {
    private long count;
    private long sum;
    private double avg;
    private int percent1;
    private int percent2;
    private int percent3;
    private int percent4;
    private int percent5;

    @Builder
    public ReviewRatioDTO(long count, long sum, double avg){
        this.count = count;
        this.sum = sum;
        this.avg = avg;

    }
}
