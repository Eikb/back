package com.farukgenc.boilerplate.springboot.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer productNumber;
    private Double price;
    private Integer stock;
    private String productName;
    private Integer EAN;

}
