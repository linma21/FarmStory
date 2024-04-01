package kr.co.farmstory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodno;
    private String prodname;
    private String type;
    private int stock;
    private int price;
    private int discount;
    private String company;

}

