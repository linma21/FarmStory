package kr.co.farmstory.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodno;
    private String prodname;
    private String thumbnail;
    private String cate;
    private int stock;
    private int price;
    private int discount;
    private int delCost;
    private int delType;
    private String company;
    @CreationTimestamp
    private LocalDateTime rdate;
}

