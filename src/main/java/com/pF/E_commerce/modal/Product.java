package com.pF.E_commerce.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private int sellingPrice;
    private int quantity;
    private String image_url;
    @ManyToOne
    private Category category;
    private LocalDateTime createdAt;
}
