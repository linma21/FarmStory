package kr.co.farmstory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "review")
public class Review {
    @Id
    private int rno;
    private String uid;
    private int prodno;
    private int score;
    private String thumbnail;
}
