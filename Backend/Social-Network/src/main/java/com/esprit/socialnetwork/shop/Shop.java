package com.esprit.socialnetwork.shop;

import com.esprit.socialnetwork.product.Product;
import com.esprit.socialnetwork.seller.Seller;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @OneToOne
    @JoinColumn(name = "owner")
    private Seller owner;
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy="shop")
    private List<Product> products;

}
