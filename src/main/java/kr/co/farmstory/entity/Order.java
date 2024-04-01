package kr.co.farmstory.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo;

    private String uid;
    private Integer orderCount;
    private LocalDateTime orderDate;
    private Integer totalPrice;
    private String reciver;
    private String rechp;
    private String recaddr;
    private String payment;
    private String memo;
    private String status;

    @CreationTimestamp
    private LocalDateTime rdate;


}

