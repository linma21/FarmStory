package kr.co.farmstory.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    private int ano;
    private String uid;
    private String comment;

    @CreationTimestamp
    private LocalDateTime rdate;

}

