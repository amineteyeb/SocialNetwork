package com.esprit.socialnetwork.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer numberOfProducts;


}
