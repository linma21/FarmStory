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
    private int amount;
    private String thumbnail;
    private String cate;
    private int price;
    private int stock;
    private int delCost;
    private int delType;
    private int discount;
    private String etc;
}

