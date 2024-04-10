package kr.co.farmstory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewPageResponseDTO {

    private List<ReviewDTO> dtoList;
    private int pg;
    private int size;
    private int total;
    private int last;
    private int startNo;
    private int start, end;
    private boolean prev, next;

    @Builder
    public ReviewPageResponseDTO(ReviewPageRequestDTO pageRequestDTO, List<ReviewDTO> dtoList , int total){
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.startNo = total - ((pg - 1) * size);
        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 4;

        int last = (int) (Math.ceil(total / (double) size));
        this.last = last;
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }


}