package kr.co.farmstory.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ArticleDTO {
    private int ano;
    private String cate;
    private String title;
    private String content;
    private String writer;
    private String thumbnail;
    private int file;
    private int hit;
    private int prodno;
    private LocalDateTime rdate;

    private String nick;
}
