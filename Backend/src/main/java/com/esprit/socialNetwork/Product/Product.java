package com.esprit.socialNetwork.Product;
import com.esprit.socialNetwork.Order.Cart;
import com.esprit.socialNetwork.Shop.Shop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
@Getter
@Setter
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop") // Shop relation many products 1 shop
    private Shop shop;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private boolean available;
    private Double productDiscountedPrice;
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Cart> carts;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="product_images",joinColumns = {@JoinColumn (name="product_id")},inverseJoinColumns = {@JoinColumn(name="image_id")})
private Set<ImageModel> productImages;
}
