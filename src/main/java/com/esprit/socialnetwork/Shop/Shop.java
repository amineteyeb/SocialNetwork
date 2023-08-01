package com.esprit.socialnetwork.Shop;

import com.esprit.socialnetwork.Product.Product;
import com.esprit.socialnetwork.Seller.Seller;
import com.esprit.socialnetwork.User.User;
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
@Table(name = "Shop")
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
