package com.example.edukan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageCode;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sub_category_id" , referencedColumnName = "id")
    @JsonBackReference
    private SubCategoryEntity subEntity;


    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    public ProductEntity(String name, String description, Double price, String imageCode) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageCode = imageCode;
    }
}