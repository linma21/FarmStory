package kr.co.farmstory.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ano;
    private String cate;
    private String title;
    private String content;
    private String writer;
    private String thumbnail;
    private int file;
    private int hit;
    private int prodno;

    @CreationTimestamp
    private LocalDateTime rdate;
}
