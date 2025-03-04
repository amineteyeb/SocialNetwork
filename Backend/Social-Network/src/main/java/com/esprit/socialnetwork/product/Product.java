package com.esprit.socialnetwork.product;
import com.esprit.socialnetwork.Order.Cart;
import com.esprit.socialnetwork.shop.Shop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
@Getter
@Setter
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop") // shop relation many products 1 shop
    private Shop shop;
    private String name;
    @DateTimeFormat
    private LocalDateTime createdAd;

    private String description;
    private double price;
    private String imageUrl;
    private boolean available;
    private Double productDiscountedPrice;
    private String colorCode;
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
