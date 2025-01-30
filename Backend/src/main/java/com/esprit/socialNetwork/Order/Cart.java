package com.esprit.socialNetwork.Order;

import com.esprit.socialNetwork.Product.Product;
import com.esprit.socialNetwork.User.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Cart(){

    }

    public Cart(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
